package io.netty.example.dubbo.proxy;

import dubbo.bean.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * todo 描述作用
 *
 * @author 逼哥
 * @date 2019/12/4
 */
@AllArgsConstructor
public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    private Map<String, Object> handlerMap;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest req = (RpcRequest) msg;
        if (handlerMap.containsKey(req.getClassName())) {
            Object clazz = handlerMap.get(req.getClassName());
            Method method = clazz.getClass().getMethod(req.getMethodName(), req.getTypes());
            Object result = method.invoke(clazz, req.getParams());
            ctx.write(result);
            ctx.flush();
            ctx.close();
        }
    }
}
