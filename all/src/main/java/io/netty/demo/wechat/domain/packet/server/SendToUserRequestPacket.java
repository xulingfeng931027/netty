package io.netty.demo.wechat.domain.packet.server;


import io.netty.demo.wechat.domain.packet.Packet;
import io.netty.demo.wechat.protocol.Command;

/**
 * 收发消息的数据包
 */
public class SendToUserRequestPacket extends Packet {
    /**
     * 消息内容
     */
    private String message;
    /**
     * 标识消息要发给哪个用户
     */
    private String toUserId;

    public SendToUserRequestPacket(String toUserId, String message) {
        this.message = message;
        this.toUserId = toUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.SEND_TO_USER_REQUEST;
    }
}
