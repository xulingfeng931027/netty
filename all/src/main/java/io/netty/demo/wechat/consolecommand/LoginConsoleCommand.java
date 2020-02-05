package io.netty.demo.wechat.consolecommand;

import io.netty.channel.Channel;
import wechat.domain.packet.server.LoginRequestPacket;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        //如果没登录就要求登录
        System.out.println("输入用户名登录");
        String username = scanner.nextLine();
        //默认密码
        loginRequestPacket.setUsername(username);
        loginRequestPacket.setPassword("pwd");
        //发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
