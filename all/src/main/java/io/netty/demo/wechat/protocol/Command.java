package io.netty.demo.wechat.protocol;

/**
 * 定义序列化请求类型的常量类
 */
public interface Command {
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte SEND_TO_USER_REQUEST = 3;
    Byte SEND_TO_USER_RESPONSE = 4;
    Byte CREATE_GROUP_REQUEST = 5;
    Byte CREATE_GROUP_RESPONSE = 6;
    Byte JOIN_GROUP_REQUEST = 7;
    Byte JOIN_GROUP_RESPONSE = 8;
    Byte GROUP_MESSAGE_REQUEST = 9;
    Byte GROUP_MESSAGE_RESPONSE = 10;
    Byte LOG_OUT_REQUEST = 11;
    Byte LOG_OUT_RESPONSE = 12;


}
