package io.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient
{
    public static void main(String[] args)
    {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInboundHandlerAdapter()).connect("127.0.0.1", 8888);
    }
}
