package io.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer
{
    public static void main(String[] args)
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class)
            .group(bossGroup, workGroup)
            .handler(new ChannelInitializer<NioSocketChannel>()
            {
                @Override
                protected void initChannel(NioSocketChannel ctx)
                    throws Exception
                {
//                    ctx.pipeline().addLast()
                }

            });
    }
}
