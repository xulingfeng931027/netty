package io.netty.demo.wechat.serializer;

/**
 * 定义序列化请求类型的常量类
 */
public interface Command
{
    Byte LOGIN_REQUEST=1;

    Byte LOGIN_RESPONSE=2;
}
