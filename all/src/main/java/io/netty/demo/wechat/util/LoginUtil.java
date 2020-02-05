package io.netty.demo.wechat.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

@Deprecated
public class LoginUtil {
    private static final AttributeKey<Boolean> LOGIN = AttributeKey.valueOf("login");

    /**
     * 标记是否已经登录
     *
     * @param channel
     */
    public static void markAsLogin(Channel channel) {
        channel.attr(LOGIN).set(true);
    }

    /**
     * 判断是否已经登录
     *
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> attr = channel.attr(LOGIN);
        if (attr.get() == null) {
            return false;
        }
        return attr.get();
    }
}
