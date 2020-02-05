package io.netty.example.dubbo.service;

import dubbo.ZKConfig;
import dubbo.loadBanlance.LoadBalance;
import dubbo.loadBanlance.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

public class ZKServiceDiscovery implements IServiceDiscovery {

    private static CuratorFramework curatorFramework;

    static {
        curatorFramework = CuratorFrameworkFactory.newClient(ZKConfig.CONNECTION_STR, new ExponentialBackoffRetry(1000, 10));
        curatorFramework.start();
    }

    List<String> repos = new ArrayList<>();

    public static void main(String[] args) {
        IServiceDiscovery discovery = new ZKServiceDiscovery();
        System.out.println(discovery.discover("advert"));
    }

    /**
     * 根据服务名发现调用ip
     *
     * @param serviceName
     * @return
     */
    @Override
    public String discover(String serviceName) {
        String path = ZKConfig.ZK_REGISTRY_PATH + "/" + serviceName;

        try {
            repos = curatorFramework.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //订阅监听服务的url
        registerWatch(path);
        LoadBalance loadBalance = new RandomLoadBalance();
        return loadBalance.select(repos);
    }

    private void registerWatch(String path) {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener pathChildrenCacheListener = (client, event) -> {
            repos = curatorFramework.getChildren().forPath(path);
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            System.out.println("注册pathChild watcher异常");
        }
    }
}
