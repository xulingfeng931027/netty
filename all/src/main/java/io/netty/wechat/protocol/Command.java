package io.netty.wechat.protocol;

/**
 * 定义序列化请求类型的常量类
 */
public interface Command
{
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;
}
