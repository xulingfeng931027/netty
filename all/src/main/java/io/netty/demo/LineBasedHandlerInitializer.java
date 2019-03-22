package io.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * 处理由行尾符分割的帧
 */
public class LineBasedHandlerInitializer extends ChannelInitializer<Channel>
{
    @Override
    protected void initChannel(Channel ch)
        throws Exception
    {
        ch.pipeline().addLast(new LineBasedFrameDecoder(64 * 1024)).addLast(new FrameHandler());
    }

    private class FrameHandler extends SimpleChannelInboundHandler<ByteBuf>
    {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
            throws Exception
        {
            //传入单个帧的内容
        }
    }
}
