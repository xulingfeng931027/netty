package io.netty.example.dubbo.rpc;

import dubbo.annotation.RpcClass;
import dubbo.api.ITest;
import dubbo.api.TestImpl;
import dubbo.proxy.RpcServerHandler;
import dubbo.registry.RegistryCenter;
import dubbo.registry.ZKRegistryCenter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * todo 描述作用
 *
 * @author 逼哥
 * @date 2019/12/4
 */
@Data
public class RpcServer {
    private Map<String, Object> handlerMap = new HashMap<>();
    private String serviceAddress;
    private RegistryCenter registryCenter;

    public RpcServer(String serviceAddress, RegistryCenter registryCenter) {
        this.serviceAddress = serviceAddress;
        this.registryCenter = registryCenter;
    }

    public static void main(String[] args) {
        //传入注册中心和发布的pi地址
        RpcServer rpcServer = new RpcServer("127.0.0.1:8088", new ZKRegistryCenter());
        ITest test = new TestImpl();
        rpcServer.bind(test);
        rpcServer.publish();
    }

    private void bind(Object... services) {
        for (Object service : services) {
            RpcClass annotation = service.getClass().getAnnotation(RpcClass.class);
            String serviceName = annotation.type().getName();
            handlerMap.put(serviceName, service);
        }
    }

    public void publish() {
        for (String serviceName : handlerMap.keySet()) {
            registryCenter.registry(serviceName, serviceAddress);
        }
        try {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(
                            new ChannelInitializer<NioSocketChannel>() {
                                @Override
                                protected void initChannel(NioSocketChannel ch) throws Exception {
                                    ChannelPipeline pipeline = ch.pipeline();
                                    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4)).addLast(
                                            new ObjectDecoder(
                                                    Integer.MAX_VALUE,
                                                    ClassResolvers.weakCachingResolver(
                                                            (this.getClass().getClassLoader()))));
                                    pipeline.addLast(new ObjectEncoder())
                                            .addLast(new RpcServerHandler(handlerMap));
                                }
                            })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            String[] addrs = serviceAddress.split(":");
            String ip = addrs[0];
            int port = Integer.parseInt(addrs[1]);
            bootstrap.bind(port).sync();
            System.out.println("启动netty服务端成功,等待连接");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
