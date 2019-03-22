package io.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.demo.heartbeat.IdleStateHandlerInitializer;
import io.netty.util.AttributeKey;

public class NettyServer
{
    public static void main(String[] args)
        throws InterruptedException
    {
        final AttributeKey<String> id = AttributeKey.valueOf("id");
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class)
            .group(bossGroup, workGroup)
            .handler(new IdleStateHandlerInitializer())
             .attr(id, "conce2019")
            .option(ChannelOption.AUTO_CLOSE, true)
            .bind(8888);
    }
}
