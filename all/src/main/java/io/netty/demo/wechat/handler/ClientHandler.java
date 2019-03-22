package io.netty.demo.wechat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.demo.wechat.serializer.LoginRequestPacket;
import io.netty.demo.wechat.serializer.LoginResponsePacket;
import io.netty.demo.wechat.serializer.Packet;
import io.netty.demo.wechat.serializer.PacketCodec;

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
                System.out.println(new Date() + "客户端登陆成功");
            }
            else
            {
                System.out.println(new Date() + "客户端登陆失败,原因:" + responsePacket.getMessage());
            }
        }
    }
}
