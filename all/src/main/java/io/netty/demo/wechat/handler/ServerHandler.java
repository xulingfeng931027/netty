package io.netty.demo.wechat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.demo.wechat.serializer.LoginRequestPacket;
import io.netty.demo.wechat.serializer.LoginResponsePacket;
import io.netty.demo.wechat.serializer.Packet;
import io.netty.demo.wechat.serializer.PacketCodec;

public class ServerHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
        throws Exception
    {
        ByteBuf byteBuf = (ByteBuf)msg;
        //解码
        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);
        //判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket)
        {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket)packet;
            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setVersion(packet.getVersion());
            //登录校验
            if (valid(loginRequestPacket))
            {
                //校验成功
                System.out.println("校验成功");
                responsePacket.setCode(0);
            }
            else
            {
                System.out.println("校验失败");
                responsePacket.setCode(1);
                responsePacket.setMessage("用户名或密码错误");
            }
            ByteBuf responseBytebuf = PacketCodec.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.writeAndFlush(responseBytebuf);
        }


    }

    private boolean valid(LoginRequestPacket loginRequestPacket)
    {
        return "flash".equals(loginRequestPacket.getUsername()) && "pwd".equals(loginRequestPacket.getPassword());
    }
}
