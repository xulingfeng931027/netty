package io.netty.demo.wechat.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wechat.domain.packet.client.JoinGroupResponsePacket;
import wechat.util.SessionUtil;

/**
 * @author 徐凌峰
 * @date 2019/4/3
 * 处理加群结果
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {
        System.out.println(SessionUtil.getSession(ctx.channel()).getUserName() + "加入群[" + msg.getGroupId() + "]成功");
    }
}
