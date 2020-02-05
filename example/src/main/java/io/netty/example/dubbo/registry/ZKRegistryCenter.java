package io.netty.example.dubbo.registry;

import dubbo.ZKConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * todo 描述作用
 *
 * @author 逼哥
 * @date 2019/12/3
 */
public class ZKRegistryCenter implements RegistryCenter {


    private static CuratorFramework curatorFramework;

    static {
        curatorFramework = CuratorFrameworkFactory.newClient(ZKConfig.CONNECTION_STR, new ExponentialBackoffRetry(1000, 10));
        curatorFramework.start();
    }

    public static void main(String[] args) {
        RegistryCenter registryCenter = new ZKRegistryCenter();
        registryCenter.registry("advert", "127.0.0.1:8088");
        while (true) {

        }
    }

    /**
     * 注册服务到注册中心
     *
     * @param serviceName    服务名
     * @param serviceAddress 服务地址
     */
    @Override
    public void registry(String serviceName, String serviceAddress) {
        String servicePath = ZKConfig.ZK_REGISTRY_PATH + "/" + serviceName;
        try {
            //根据服务名创建zk节点
            if (curatorFramework.checkExists().forPath(servicePath) == null) {
                curatorFramework.create().
                        creatingParentsIfNeeded().
                        withMode(CreateMode.PERSISTENT).
                        forPath(servicePath, "0".getBytes());
            }
            String addressPath = servicePath + "/" + serviceAddress;
            //将服务地址设置为一个临时节点,断开后自动删除啦
            String rsNode = curatorFramework.create()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(addressPath, "0".getBytes());
            System.out.println("服务注册成功:" + rsNode);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务注册失败");
        }
    }

}
