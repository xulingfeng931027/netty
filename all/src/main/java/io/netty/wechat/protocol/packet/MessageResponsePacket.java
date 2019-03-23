package io.netty.wechat.protocol.packet;

import io.netty.wechat.protocol.Command;

/**
 * 收发消息的数据包
 */
public class MessageResponsePacket extends Packet
{
    private String message;
    @Override
    public Byte getCommand()
    {
        return Command.MESSAGE_RESPONSE;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
