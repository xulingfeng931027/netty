package io.netty.demo.wechat.handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author 逼哥
 * @date 2019/4/9
 */
public class IMIdleHandler extends IdleStateHandler {

    private static final int MAX_WAIT_TIME = 10;

    public IMIdleHandler() {
        super(MAX_WAIT_TIME, 10, 10, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(MAX_WAIT_TIME + "秒没读到数据,连接关闭");
        ctx.channel().close();
    }

}
