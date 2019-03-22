package io.netty.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch)
        throws Exception
    {
        ch.pipeline().addLast(new HttpServerCodec(),
            new HttpObjectAggregator(65536),
            new WebSocketServerProtocolHandler("/websocket"),
            new TextWebSocketFrameHandler());
    }

    private class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrameHandler>
    {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrameHandler msg)
            throws Exception
        {
            //处理Text frame
        }
    }
}
