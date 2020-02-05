package io.netty.demo.wechat.util;

import java.util.UUID;

public final class IDUtil {
    private IDUtil() {

    }

    public static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

}
