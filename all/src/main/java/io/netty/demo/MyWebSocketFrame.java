package io.netty.demo;

import io.netty.buffer.ByteBuf;

public final class MyWebSocketFrame
{
    public enum FrameType
    {
        PING, PONG
    }

    private final FrameType type;

    private final ByteBuf data;

    public MyWebSocketFrame(FrameType type, ByteBuf data)
    {
        this.type = type;
        this.data = data;
    }

    public FrameType getType()
    {
        return type;
    }

    public ByteBuf getData()
    {
        return data;
    }

}
