package io.netty.demo.wechat.domain.packet.server;


import io.netty.demo.wechat.domain.packet.Packet;
import io.netty.demo.wechat.protocol.Command;

import java.util.List;

public class CreateGroupRequestPacket extends Packet {
    private List<String> userIdList;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
