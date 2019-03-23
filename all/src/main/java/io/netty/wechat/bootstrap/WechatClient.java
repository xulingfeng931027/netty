package io.netty.wechat.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.wechat.handler.ClientHandler;
import io.netty.wechat.protocol.PacketCodec;
import io.netty.wechat.protocol.packet.MessageRequestPacket;
import io.netty.wechat.util.LoginUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
            });
        connect(bootstrap);
    }

    private static void connect(Bootstrap bootstrap)
    {
        bootstrap.connect("127.0.0.1", 8888).addListener(future -> {
            if (future.isSuccess())
            {
                Channel channel = ((ChannelFuture)future).channel();
                //连接成功后,启动控制台线程
                startConsoleThread(channel);
            }
        });
    }

    private static void startConsoleThread(Channel channel)
    {
        new Thread(() -> {
            while (!Thread.interrupted())
            {
                if (LoginUtil.hasLogin(channel))
                {
                    try
                    {
                        System.out.println("输入消息并发送至服务端");
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                        String line = bufferedReader.readLine();
                        MessageRequestPacket packet = new MessageRequestPacket();
                        packet.setMessage(line);

                        ByteBuf byteBuf = PacketCodec.INSTANCE.encode(channel.alloc(), packet);
                        channel.writeAndFlush(byteBuf);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

    }
}
