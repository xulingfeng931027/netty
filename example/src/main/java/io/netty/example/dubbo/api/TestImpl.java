package io.netty.example.dubbo.api;

import dubbo.annotation.RpcClass;

/**
 * todo 描述作用
 *
 * @author 逼哥
 * @date 2019/12/4
 */
@RpcClass(type = ITest.class)
public class TestImpl implements ITest {
    @Override
    public String Test(String msg) {
        return "Hello " + msg;
    }
}
