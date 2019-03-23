package io.netty.wechat.protocol.packet;

import io.netty.wechat.protocol.Command;

/**
 * 登录请求的对象
 */
public class LoginResponsePacket extends Packet
{

   private Integer code;
   private String message;

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public Byte getCommand()
    {
        return Command.LOGIN_RESPONSE;
    }
}
