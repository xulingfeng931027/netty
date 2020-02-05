package io.netty.demo.wechat.consolecommand;

import io.netty.channel.Channel;
import wechat.domain.packet.server.SendToUserRequestPacket;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        String toUserId = scanner.next();
        String message = scanner.next();
        //发送消息
        channel.writeAndFlush(new SendToUserRequestPacket(toUserId, message));
    }
}
