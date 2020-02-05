package io.netty.demo.wechat.handler.server;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.demo.wechat.domain.packet.server.LogoutRequestPacket;

/**
 * @author 徐凌峰
 * @date 2019/4/7
 * 功能描述
 */
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {

    }
}
