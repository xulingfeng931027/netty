package io.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

public class WebSocketConvertHandler extends MessageToMessageCodec<WebSocketFrame, MyWebSocketFrame>
{
    /**
     * 根据frametype将mywebsocket编码为websokectframe
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     * 解码  将in转为out in看做是通过网络发送的数据类型
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, MyWebSocketFrame msg, List<Object> out)
        throws Exception
    {
        ByteBuf payload = msg.getData().retainedDuplicate();
        switch (msg.getType())
        {
            //实例化一个指定子类型的websocketframe
            case PING:
                out.add(new PingWebSocketFrame());
                break;
            case PONG:
                out.add(new PongWebSocketFrame());
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * 将websocket解码为MyWebSocket,并设置frametype
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out)
        throws Exception
    {
        ByteBuf payload = msg.content().duplicate().retain();
        if (msg instanceof PingWebSocketFrame)
        {
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.PING, payload));
        }
        if (msg instanceof PongWebSocketFrame)
        {
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.PONG, payload));
        }
    }
}
