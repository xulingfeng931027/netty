package io.netty.demo.wechat.handler.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wechat.domain.packet.Packet;

import java.util.HashMap;
import java.util.Map;

import static wechat.protocol.Command.*;

/**
 * @author 徐凌峰
 * @date 2019/4/7
 * 功能描述
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    private static final IMHandler INSTANCE = new IMHandler();
    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put(CREATE_GROUP_REQUEST, new CreateGroupRequestHandler());
        handlerMap.put(GROUP_MESSAGE_REQUEST, new GroupMessageRequestHandler());
        handlerMap.put(LOG_OUT_REQUEST, new LogoutRequestHandler());
        handlerMap.put(JOIN_GROUP_REQUEST, new JoinGroupRequestHandler());
        handlerMap.put(SEND_TO_USER_REQUEST, new SendToUserRequestHandler());
    }

    public static IMHandler getInstance() {
        return INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
