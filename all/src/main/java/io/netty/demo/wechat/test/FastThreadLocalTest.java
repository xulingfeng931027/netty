package io.netty.demo.wechat.test;

import io.netty.util.concurrent.FastThreadLocal;

/**
 * @author 逼哥
 * @date 2019/5/15
 */
public class FastThreadLocalTest {

    private static FastThreadLocal<Integer> threadLocal = new FastThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set(1);
        new Thread(()->{
            threadLocal.set(2);
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get());
        }).start();

    }

}
