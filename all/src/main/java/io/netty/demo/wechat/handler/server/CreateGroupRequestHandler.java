package io.netty.demo.wechat.handler.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import wechat.domain.packet.client.CreateGroupResponsePacket;
import wechat.domain.packet.server.CreateGroupRequestPacket;
import wechat.util.IDUtil;
import wechat.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIdList = msg.getUserIdList();
        List<String> usernameList = new ArrayList<>();
        //创建一个channel分组,统一管理一些channel
        ChannelGroup channels = new DefaultChannelGroup(ctx.executor());
        //将待加入群聊的用户拿出来
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channels.add(channel);
                channels.add(ctx.channel());
                usernameList.add(SessionUtil.getSession(channel).getUserName());
                //再把自己也放进去
                usernameList.add(SessionUtil.getSession(ctx.channel()).getUserName());
            }
        }
        //创造结果响应
        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setGroupId(IDUtil.randomUserId());
        responsePacket.setUsernameList(usernameList);
        SessionUtil.bindChannelGroup(responsePacket.getGroupId(), channels);
        System.out.print("群创建成功，id 为[" + responsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + responsePacket.getUsernameList());
        //给每个客户端发送通知
        ctx.channel().writeAndFlush(responsePacket);
        channels.writeAndFlush(responsePacket);


    }
}
