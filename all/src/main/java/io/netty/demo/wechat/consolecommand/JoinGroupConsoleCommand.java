package io.netty.demo.wechat.consolecommand;

import io.netty.channel.Channel;
import wechat.domain.packet.server.JoinGroupRequestPacket;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket requestPacket = new JoinGroupRequestPacket();
        System.out.println("输入groupId,加入群聊:");
        String groupId = scanner.next();
        requestPacket.setGroupId(groupId);
        //从用户输入中获取要加入那个群聊,再发送给服务端.
        channel.writeAndFlush(requestPacket);
    }
}
