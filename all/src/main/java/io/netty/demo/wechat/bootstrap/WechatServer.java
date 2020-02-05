package io.netty.demo.wechat.bootstrap;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import wechat.handler.demo.*;

/**
 * 所有指令都实现完之后，我们发现我们的 handler 已经非常臃肿庞大了，
 * 接下来，我们通过单例模式改造、编解码器合并、平行指令 handler 合并、慎重选择两种类型的 writeAndFlush() 的方式来压缩优化。
 * 在 handler 的处理中，如果有耗时的操作，我们需要把这些操作都丢到我们自定义的的业务线程池中处理，
 * 因为 NIO 线程是会有很多 channel 共享的，我们不能阻塞他。
 * 对于统计耗时的场景，如果在自定义业务线程中调用类似 writeAndFlush() 的异步操作，需要通过添加监听器的方式来统计。
 */
public class WechatServer {
    public static void main(String[] args)
            throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .group(bossGroup, workGroup)
                //Netty 在这里的逻辑是：每次有新连接到来的时候，都会调用 ChannelInitializer的initChannel() 方法，
                //然后这里一堆指令相关的 handler 都会被 new 一次。
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch)
                            throws Exception {
                        ch.pipeline().addLast(new InBoundHandlerA());
                        ch.pipeline().addLast(new InBoundHandlerB());
                        ch.pipeline().addLast(new InBoundHandlerC());

                        ch.pipeline().addLast(new OutboundHandlerA());
                        ch.pipeline().addLast(new OutboundHandlerB());
                        ch.pipeline().addLast(new OutboundHandlerC());
//                        ch.pipeline().addLast(new IMIdleHandler());
//                        ch.pipeline().addLast(new Spliter(Integer.MAX_VALUE));
//                        ch.pipeline().addLast(PacketCodecHandler.getInstance());
//                        ch.pipeline().addLast(LoginRequestHandler.getInstance());
//                        ch.pipeline().addLast(new AuthHandler());
//                        ch.pipeline().addLast(IMHandler.getInstance());
                    }
                }).bind(8088);

    }
}