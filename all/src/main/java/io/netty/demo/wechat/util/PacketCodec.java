package io.netty.demo.wechat.util;


import io.netty.buffer.ByteBuf;
import io.netty.demo.wechat.domain.packet.Packet;
import io.netty.demo.wechat.domain.packet.client.*;
import io.netty.demo.wechat.domain.packet.server.*;
import io.netty.demo.wechat.protocol.Command;
import io.netty.demo.wechat.protocol.Constant;
import io.netty.demo.wechat.protocol.JsonSerializer;
import io.netty.demo.wechat.protocol.Serializer;

import java.util.HashMap;
import java.util.Map;

import static io.netty.demo.wechat.protocol.Command.*;

/**
 * @author 徐凌峰
 * 这是一个编解码具体逻辑类
 * 首先，我们需要创建一个 ByteBuf，这里我们调用 Netty 的 ByteBuf 分配器来创建，ioBuffer()方法会返回适配 io 读写相关的内存，
 * 它会尽可能创建一个直接内存，直接内存可以理解为不受 jvm 堆管理的内存空间，写到 IO 缓冲区的效果更高。
 * 接下来，我们将 Java 对象序列化成二进制数据包。
 * 最后，我们对照本小节开头协议的设计以及上一小节 ByteBuf 的 API，逐个往 ByteBuf 写入字段，即实现了编码过程，到此，编码过程结束。
 */
public class PacketCodec {
    public static final int MAGIC_NUMBER = 0x12345678;
    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(SEND_TO_USER_REQUEST, SendToUserRequestPacket.class);
        packetTypeMap.put(SEND_TO_USER_RESPONSE, SendToUserResponsePacket.class);
        packetTypeMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);


        serializerMap = new HashMap<>();
        Serializer serializer = new JsonSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public static final PacketCodec INSTANCE = new PacketCodec();


    public void encode(ByteBuf byteBuf, Packet msg)
            throws Exception {
        byte[] bytes = Serializer.DEFAULT.serialize(msg);

        //实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(msg.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(msg.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {
        //跳过魔数
        byteBuf.skipBytes(Constant.INT_BYTES);
        //跳过版本号
        byteBuf.skipBytes(1);
        //序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();
        //指令
        byte command = byteBuf.readByte();
        //数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];

        byteBuf.readBytes(bytes);

        //根据command获取请求的实际类型
        Class<? extends Packet> requestType = getRequestType(command);

        //根据序列化算法决定序列化方法
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (serializer != null && requestType != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

}
