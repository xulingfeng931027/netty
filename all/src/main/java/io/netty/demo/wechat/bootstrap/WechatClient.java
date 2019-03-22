package io.netty.demo.wechat.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.demo.wechat.handler.ClientHandler;

public class WechatClient
{
    public static void main(String[] args)
    {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>()
            {
                @Override
                protected void initChannel(SocketChannel ch)
                    throws Exception
                {
                    ch.pipeline().addLast(new ClientHandler());
                }
            }).connect("127.0.0.1", 8888);
    }
}
