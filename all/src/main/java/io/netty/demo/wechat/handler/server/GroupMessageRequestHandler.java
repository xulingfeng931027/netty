package io.netty.demo.wechat.handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import wechat.domain.packet.client.GroupMessageResponsePacket;
import wechat.domain.packet.server.GroupMessageRequestPacket;
import wechat.util.SessionUtil;

/**
 * @author 徐凌峰
 * @date 2019/4/6
 * 功能描述
 */
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setMessage(msg.getMessage());
        groupMessageResponsePacket.setUsername(msg.getUsername());
        System.out.println("群" + msg.getGroupId() + "中的" + msg.getUsername() + "发送了一条群消息:" + msg.getMessage());
        channelGroup.writeAndFlush(groupMessageResponsePacket);
    }
}
