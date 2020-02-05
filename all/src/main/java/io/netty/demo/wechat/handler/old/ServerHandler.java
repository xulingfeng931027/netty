package io.netty.demo.wechat.handler.old;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import wechat.domain.packet.Packet;
import wechat.domain.packet.client.LoginResponsePacket;
import wechat.domain.packet.client.SendToUserResponsePacket;
import wechat.domain.packet.server.LoginRequestPacket;
import wechat.domain.packet.server.SendToUserRequestPacket;
import wechat.util.PacketCodec;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当有连接读取数据时调用
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        //解码
        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);
        //判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setVersion(packet.getVersion());
            //登录校验
            if (valid(loginRequestPacket)) {
                //校验成功
                System.out.println("校验成功");
                responsePacket.setCode(0);
            } else {
                System.out.println("校验失败");
                responsePacket.setCode(1);
                responsePacket.setMessage("用户名或密码错误");
            }
//            ByteBuf responseBytebuf = PacketCodec.INSTANCE.encode(ctx.alloc(), responsePacket);
//            ctx.writeAndFlush(responseBytebuf);
        } else if (packet instanceof SendToUserRequestPacket) {
            SendToUserRequestPacket sendToUserRequestPacket = (SendToUserRequestPacket) packet;
            System.out.println(new Date() + "收到客户端消息:" + sendToUserRequestPacket.getMessage());
            SendToUserResponsePacket sendToUserResponsePacket = new SendToUserResponsePacket();
            sendToUserResponsePacket.setMessage("服务端回复:【" + "response:" + sendToUserRequestPacket.getMessage() + "】");
//            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), sendToUserResponsePacket);
//            ctx.channel().writeAndFlush(responseByteBuf);

        }
        super.channelRead(ctx, msg);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return "flash".equals(loginRequestPacket.getUsername()) && "pwd".equals(loginRequestPacket.getPassword());
    }
}
