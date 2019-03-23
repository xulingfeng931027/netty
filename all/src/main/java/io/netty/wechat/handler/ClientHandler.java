package io.netty.wechat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.wechat.protocol.packet.LoginRequestPacket;
import io.netty.wechat.protocol.packet.LoginResponsePacket;
import io.netty.wechat.protocol.packet.MessageResponsePacket;
import io.netty.wechat.protocol.packet.Packet;
import io.netty.wechat.protocol.PacketCodec;
import io.netty.wechat.util.LoginUtil;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx)
        throws Exception
    {
        System.out.println(new Date() + ": 客户端开始登录");

        //我们实现在客户端连接上服务端之后，立即登录。
        //在连接上服务端之后，Netty 会回调到 ClientHandler 的 channelActive() 方法，
        //我们在这个方法体里面编写相应的逻辑

        //创建登录对象
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUsername("flash");
        packet.setPassword("pwd");

        //编码
        ByteBuf byteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), packet);

        //写数据到channel中
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
        ByteBuf byteBuf = (ByteBuf)msg;
        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket)
        {
            LoginResponsePacket responsePacket = (LoginResponsePacket)packet;
            if (responsePacket.getCode() == 0)
            {
                //登录成功
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() + "客户端登陆成功");
            }
            else
            {
                System.out.println(new Date() + "客户端登陆失败,原因:" + responsePacket.getMessage());
            }
        }
        else if (packet instanceof MessageResponsePacket)
        {
            //处理服务端返回的消息
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket)packet;
            System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
        }
    }
}
