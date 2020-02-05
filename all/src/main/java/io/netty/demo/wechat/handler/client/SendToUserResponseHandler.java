package io.netty.demo.wechat.handler.client;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wechat.domain.packet.client.SendToUserResponsePacket;

/**
 * @author paul
 */
public class SendToUserResponseHandler extends SimpleChannelInboundHandler<SendToUserResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToUserResponsePacket sendToUserResponsePacket)
            throws Exception {
        String fromUserId = sendToUserResponsePacket.getFromUserId();
        String fromUserName = sendToUserResponsePacket.getFromUsername();
        System.out.println(fromUserId + ":" + fromUserName + " -> " + sendToUserResponsePacket.getMessage());
    }
}
