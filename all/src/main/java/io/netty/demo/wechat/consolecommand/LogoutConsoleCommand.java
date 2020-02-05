package io.netty.demo.wechat.consolecommand;

import io.netty.channel.Channel;
import wechat.domain.Session;
import wechat.util.SessionUtil;

import java.util.Scanner;

public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        Session session = SessionUtil.getSession(channel);
        SessionUtil.unBindSession(channel);
        System.out.println("用户"+session.getUserName()+"退出登录");
    }
}
