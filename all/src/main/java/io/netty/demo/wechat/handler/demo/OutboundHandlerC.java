package io.netty.demo.wechat.handler.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author 徐凌峰
 * @date 2019/5/12
 * 功能描述
 */
public class OutboundHandlerC extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("outBountHandlerC" + msg);
        ctx.write(msg, promise);
    }


}
