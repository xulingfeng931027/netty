package io.netty.example.dubbo.service;

public interface IServiceDiscovery {

    /**
     * 根据服务名发现调用ip
     * @param serviceName
     * @return
     */
    String discover(String serviceName);
}
