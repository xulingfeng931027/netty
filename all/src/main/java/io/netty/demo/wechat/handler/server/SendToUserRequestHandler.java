package io.netty.demo.wechat.handler.server;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.demo.wechat.domain.Session;
import io.netty.demo.wechat.domain.packet.client.SendToUserResponsePacket;
import io.netty.demo.wechat.domain.packet.server.SendToUserRequestPacket;
import io.netty.demo.wechat.util.SessionUtil;

/**
 * @author paul
 */
public class SendToUserRequestHandler extends SimpleChannelInboundHandler<SendToUserRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToUserRequestPacket sendToUserRequestPacket)
            throws Exception {
        //1.拿到消息所属的session
        Session session = SessionUtil.getSession(ctx.channel());
        //2.构造要发送的消息
        SendToUserResponsePacket sendToUserResponsePacket = new SendToUserResponsePacket();
        sendToUserResponsePacket.setFromUserId(session.getUserId());
        sendToUserResponsePacket.setFromUsername(session.getUserName());
        sendToUserResponsePacket.setMessage(sendToUserRequestPacket.getMessage());
        //3.拿到消息接收方的channel
        Channel toUserChannel = SessionUtil.getChannel(sendToUserRequestPacket.getToUserId());
        //4.将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(sendToUserResponsePacket);
        } else {
            System.err.println("[" + sendToUserRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }

    }

}
