package io.netty.demo.wechat.domain.packet.client;


import io.netty.demo.wechat.domain.packet.Packet;
import io.netty.demo.wechat.protocol.Command;

import java.util.List;

public class CreateGroupResponsePacket extends Packet {
    private String groupId;
private List<String> usernameList;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getUsernameList() {
        return usernameList;
    }

    public void setUsernameList(List<String> usernameList) {
        this.usernameList = usernameList;
    }

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
