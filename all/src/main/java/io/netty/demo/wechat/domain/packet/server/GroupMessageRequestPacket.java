package io.netty.demo.wechat.domain.packet.server;

import io.netty.demo.wechat.domain.packet.Packet;
import io.netty.demo.wechat.protocol.Command;

/**
 * @author 徐凌峰
 * @date 2019/4/6
 * 功能描述
 */
public class GroupMessageRequestPacket extends Packet {

    private String groupId;
    private String message;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
