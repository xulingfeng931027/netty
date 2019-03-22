package io.netty.demo.heartbeat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class IdleStateHandlerInitializer extends ChannelInitializer<Channel>
{
    @Override
    protected void initChannel(Channel ch)
        throws Exception
    {
        ch.pipeline()
            .addLast(new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS))
            .addLast(new HeartBeatHandler());
    }
}
