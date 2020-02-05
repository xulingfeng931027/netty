package io.netty.example.dubbo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * todo 描述作用
 *
 * @author 逼哥
 * @date 2019/12/4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcClass {

    Class<?> type();

}

