package io.netty.demo.wechat.handler.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.demo.wechat.domain.Session;
import io.netty.demo.wechat.domain.packet.client.LoginResponsePacket;
import io.netty.demo.wechat.domain.packet.server.LoginRequestPacket;
import io.netty.demo.wechat.util.IDUtil;
import io.netty.demo.wechat.util.SessionUtil;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    private LoginRequestHandler() {

    }

    private static final LoginRequestHandler HANDLER = new LoginRequestHandler();

    public static LoginRequestHandler getInstance() {
        return HANDLER;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg)
            throws Exception {
//        ctx.pipeline().writeAndFlush(login(msg));  //跟下面一个效果
        // 给客户端发送登录响应
        ctx.channel().writeAndFlush(login(ctx, msg));
    }

    private LoginResponsePacket login(ChannelHandlerContext ctx, LoginRequestPacket msg) {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(msg.getVersion());
        responsePacket.setUserName(msg.getUsername());
        //登录校验
        if (valid(msg)) {
            //校验成功
            System.out.println("登录成功");
            responsePacket.setCode(0);
            String userId = IDUtil.randomUserId();
            responsePacket.setUserId(userId);
            SessionUtil.bindSession(new Session(userId, msg.getUsername()), ctx.channel());
        } else {
            System.out.println("登录失败");
            responsePacket.setCode(1);
            responsePacket.setMessage("用户名或密码错误");
        }
        return responsePacket;
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
