package io.netty.example.dubbo.registry;

/**
 * todo 描述作用
 *
 * @author 逼哥
 * @date 2019/12/3
 */
public interface RegistryCenter {
    /**
     * 注册服务到注册中心
     * @param serviceName 服务名
     * @param serviceAddress 服务地址
     */
    void registry(String serviceName, String serviceAddress);

}
