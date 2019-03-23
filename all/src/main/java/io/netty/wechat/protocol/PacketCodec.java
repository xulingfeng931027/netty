package io.netty.wechat.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.wechat.protocol.packet.*;

/**
 * 首先，我们需要创建一个 ByteBuf，这里我们调用 Netty 的 ByteBuf 分配器来创建，ioBuffer()方法会返回适配 io 读写相关的内存，
 * 它会尽可能创建一个直接内存，直接内存可以理解为不受 jvm 堆管理的内存空间，写到 IO 缓冲区的效果更高。
 * 接下来，我们将 Java 对象序列化成二进制数据包。
 * 最后，我们对照本小节开头协议的设计以及上一小节 ByteBuf 的 API，逐个往 ByteBuf 写入字段，即实现了编码过程，到此，编码过程结束。
 */
public class PacketCodec
{
    private static final int MAGIC_NUMBER = 0x12345678;

    private PacketCodec()
    {
    }

    public static final PacketCodec INSTANCE = new PacketCodec();

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet msg)
        throws Exception
    {
        ByteBuf byteBuf = byteBufAllocator.buffer();
        byte[] bytes = Serializer.DEFAULT.serialize(msg);

        //实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(msg.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(msg.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf)
    {
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

        if (serializer != null && requestType != null)
        {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command)
    {
        if (Command.LOGIN_REQUEST.equals(command))
        {
            return LoginRequestPacket.class;
        }
       else if (Command.LOGIN_RESPONSE.equals(command))
        {
            return LoginResponsePacket.class;
        }
        else if (Command.MESSAGE_REQUEST.equals(command))
        {
            return MessageRequestPacket.class;
        }
        else if (Command.MESSAGE_RESPONSE.equals(command))
        {
            return MessageResponsePacket.class;
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorittm)
    {
        return Serializer.DEFAULT;
    }

}
