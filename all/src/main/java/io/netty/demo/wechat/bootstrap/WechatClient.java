package io.netty.demo.wechat.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import wechat.consolecommand.ConsoleCommandManager;
import wechat.consolecommand.LoginConsoleCommand;
import wechat.handler.PacketCodecHandler;
import wechat.handler.client.*;
import wechat.util.SessionUtil;
import wechat.util.Spliter;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class WechatClient {
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch)
                            throws Exception {
                        ch.pipeline().addLast(new Spliter(Integer.MAX_VALUE));
                        ch.pipeline().addLast(PacketCodecHandler.getInstance());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new SendToUserResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        ch.pipeline().addLast(new GroupMessageResponseHandler());
                    }
                });
        connect(bootstrap, "localhost", 8088, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }


    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                Scanner scanner = new Scanner(System.in);
                try {
                    if (SessionUtil.hasLogin(channel)) {
                        //此处由用户输入要调用什么命令,便于扩展
                        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
                        consoleCommandManager.exec(scanner, channel);
                    } else {
                        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
                        loginConsoleCommand.exec(scanner, channel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }


}
