package io.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ToIntegerDocoder2 extends ReplayingDecoder<Void>
{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
        throws Exception
    {
        //不需要检查是否有至少4个字节可读,一个int占4个字节
        //从入站bytebuf中读取一个int并添加到解码消息的list中
            out.add(in.readInt());
    }
}
