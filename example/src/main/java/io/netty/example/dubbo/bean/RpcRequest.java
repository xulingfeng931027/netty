package io.netty.example.dubbo.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * rpc请求类
 *
 * @author 逼哥
 * @date 2019/12/4
 */
@Data
public class RpcRequest implements Serializable {
    private String className;
    private String methodName;
    private Class<?>[] types;
    private Object[] params;
}
