package io.netty.demo.wechat.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import wechat.util.SessionUtil;

/**
 * 登录校验处理器,实现登录校验拦截，检测到未通过就关闭连接
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            //当第一次通过后，移除这个handler，没必要重复验证
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕,无需再次验证.移除AuthHandler");
        } else {
            System.out.println("用户未登录,客户端连接被关闭");
        }
    }
}
