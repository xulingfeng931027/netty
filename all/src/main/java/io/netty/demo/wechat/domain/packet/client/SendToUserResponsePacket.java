package io.netty.demo.wechat.domain.packet.client;


import io.netty.demo.wechat.domain.packet.Packet;
import io.netty.demo.wechat.protocol.Command;

/**
 * 收发消息的数据包
 */
public class SendToUserResponsePacket extends Packet {
    private String message;
    private String fromUserId;
    private String fromUsername;

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    @Override
    public Byte getCommand() {
        return Command.SEND_TO_USER_RESPONSE;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
