package io.netty.demo.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 基于心跳机制的检测，当空闲达到一定时间后发送心跳消息
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter
{
    private static final ByteBuf HEARTBEAT_SEQUENCE =
        Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT".getBytes()));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
        throws Exception
    {
        if (evt instanceof IdleStateEvent)
        {
            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE);
            System.out.println("连接空闲较久");
        }
        else
        {
            super.userEventTriggered(ctx, evt);
        }
    }
}
