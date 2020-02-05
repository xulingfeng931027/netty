package io.netty.demo.wechat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登录校验处理器,实现登录校验拦截，检测到未通过就关闭连接
 */
public class AuthHandlerDemo extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        //通过校验就将自身移除,否则就关闭连接
        if (valid(msg)) {
            ctx.pipeline().remove(this);
        }else {
            System.out.println("校验不通过哦");
            ctx.channel().close();
        }
    }

    private boolean valid(ByteBuf msg) {

        return false;
    }

}
