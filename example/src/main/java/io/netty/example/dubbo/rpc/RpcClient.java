package io.netty.example.dubbo.rpc;

import dubbo.api.ITest;
import dubbo.bean.RpcRequest;
import dubbo.proxy.RpcProxyHandler;
import dubbo.service.IServiceDiscovery;
import dubbo.service.ZKServiceDiscovery;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.Data;

import java.lang.reflect.Proxy;

/**
 * todo 描述作用
 *
 * @author 逼哥
 * @date 2019/12/4
 */
@Data
public class RpcClient {
    private IServiceDiscovery serviceDiscovery;

    public RpcClient(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public static void main(String[] args) {
        RpcClient rpcClient = new RpcClient(new ZKServiceDiscovery());
        ITest test = rpcClient.create(ITest.class);
        System.out.println(test.Test("paul"));
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> interfaceClass) {
        return (T)
                Proxy.newProxyInstance(
                        interfaceClass.getClassLoader(),
                        new Class<?>[]{interfaceClass},
                        (proxy, method, args) -> {
                            // 发送给服务端的数据
                            RpcRequest request = new RpcRequest();
                            request.setClassName(method.getDeclaringClass().getName());
                            request.setMethodName(method.getName());
                            request.setTypes(method.getParameterTypes());
                            request.setParams(args);
                            String serviceName = interfaceClass.getName();
                            String serviceAddress = serviceDiscovery.discover(serviceName);
                            if (serviceAddress == null) {
                                System.out.println("服务提供方地址为空");
                                return "";
                            }
                            String[] arrs = serviceAddress.split(":");
                            String host = arrs[0];
                            int port = Integer.parseInt(arrs[1]);
                            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
                            Bootstrap bootstrap = new Bootstrap();
                            RpcProxyHandler rpcProxyHandler = new RpcProxyHandler();
                            try {
                                bootstrap
                                        .group(eventLoopGroup)
                                        .channel(NioSocketChannel.class)
                                        .handler(
                                                new ChannelInitializer<SocketChannel>() {
                                                    @Override
                                                    protected void initChannel(SocketChannel ch) throws Exception {
                                                        ChannelPipeline pipeline = ch.pipeline();
                                                        pipeline.addLast(
                                                                new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
                                                        pipeline
                                                                .addLast(
                                                                        new ObjectDecoder(
                                                                                Integer.MAX_VALUE,
                                                                                ClassResolvers.softCachingResolver(null)))
                                                                .addLast(new ObjectEncoder())
                                                                .addLast(rpcProxyHandler);
                                                    }
                                                })
                                        .option(ChannelOption.TCP_NODELAY, true);
                                ChannelFuture future = bootstrap.connect(host, port).sync();
                                future.channel().writeAndFlush(request);
                                future.channel().closeFuture().sync();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                eventLoopGroup.shutdownGracefully();
                            }
                            // 这里会返回真正的方法调用返回值
                            return rpcProxyHandler.getResponse();
                        });
    }
}
