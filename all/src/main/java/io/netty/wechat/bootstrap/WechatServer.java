package io.netty.wechat.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.wechat.handler.ServerHandler;

public class WechatServer
{
    public static void main(String[] args)
        throws InterruptedException
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        ChannelFuture future = bootstrap.channel(NioServerSocketChannel.class).
            option(ChannelOption.SO_BACKLOG, 1024)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .group(bossGroup, workGroup)
            .childHandler(new ChannelInitializer<NioSocketChannel>()
            {
                @Override
                protected void initChannel(NioSocketChannel ch)
                    throws Exception
                {
                    ch.pipeline().addLast(new ServerHandler());
                }
            }).bind(8888).sync();



    }
}
