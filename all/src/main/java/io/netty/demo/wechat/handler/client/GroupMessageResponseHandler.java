package io.netty.demo.wechat.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wechat.domain.packet.client.GroupMessageResponsePacket;

/**
 * @author 徐凌峰
 * @date 2019/4/6
 * 功能描述
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) throws Exception {
        String message = msg.getMessage();
        String username = msg.getUsername();
        System.out.println("收到来自"+msg.getUsername() + "发送的一条群消息:" + msg.getMessage());
    }
}
