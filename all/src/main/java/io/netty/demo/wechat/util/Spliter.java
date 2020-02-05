package io.netty.demo.wechat.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET=7;
    private static final int LENGTH_FIELD_SIZE = 4;
    public Spliter(int maxFrameLength) {
        super(maxFrameLength, LENGTH_FIELD_OFFSET, LENGTH_FIELD_SIZE);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //通过开头的魔数判断,拒绝非本协议的连接
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            System.out.println("检测到非法数据,连接关闭,bytebuf="+new String(bytes));
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
