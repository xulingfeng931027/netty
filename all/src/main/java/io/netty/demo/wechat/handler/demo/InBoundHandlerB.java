package io.netty.demo.wechat.handler.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author 徐凌峰
 * @date 2019/5/11
 * 功能描述
 */
public class InBoundHandlerB extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().pipeline().fireChannelRead("hello world!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelInboundB:"+msg);
        ctx.fireChannelRead(msg);
    }
}
