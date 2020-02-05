package io.netty.demo.wechat.consolecommand;

import io.netty.channel.Channel;
import wechat.domain.packet.server.GroupMessageRequestPacket;
import wechat.util.SessionUtil;

import java.util.Scanner;

/**
 * @author 徐凌峰
 * @date 2019/4/6
 * 功能描述
 */
public class GroupMessageCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        GroupMessageRequestPacket requestPacket = new GroupMessageRequestPacket();
        System.out.println("输入groupid和消息,发送群消息");
        String groupId = scanner.next();
        String message = scanner.next();
        requestPacket.setGroupId(groupId);
        requestPacket.setMessage(message);
        requestPacket.setUsername(SessionUtil.getSession(channel).getUserName());
        //从用户输入中获取要加入那个群聊,再发送给服务端.
        channel.writeAndFlush(requestPacket);
    }
}