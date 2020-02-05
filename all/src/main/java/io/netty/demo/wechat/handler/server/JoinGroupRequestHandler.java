package io.netty.demo.wechat.handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import wechat.domain.packet.client.JoinGroupResponsePacket;
import wechat.domain.packet.server.JoinGroupRequestPacket;
import wechat.util.SessionUtil;

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) throws Exception {
        //1.获取群对应的channelGroup,将当前用户所属的channel塞进去
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            System.out.println(groupId + "不存在,请重新输入!");
            return;
        }
        channelGroup.add(ctx.channel());
        //2.构造响应发送给客户端
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        responsePacket.setGroupId(groupId);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
