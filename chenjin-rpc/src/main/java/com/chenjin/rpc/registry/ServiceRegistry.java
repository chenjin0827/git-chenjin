package com.chenjin.rpc.registry;

import com.chenjin.rpc.constant.Constant;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ServiceRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    //注册地址
    private String registryAddress;

    //构造方法
    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, watchedEvent -> {
                boolean b = watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected;
                if (b) {
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();//如果没有连上则等待
        } catch (IOException | InterruptedException e) {
            LOGGER.error("zookeeper连接失败");
        }
        return zk;
    }

    private void createNode(ZooKeeper zk, String data) {
        byte[] bytes = data.getBytes();
        try {
            String path = zk.create(Constant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            LOGGER.info("create zookeeper node ({} => {})", path, data);
        } catch (KeeperException e) {
            LOGGER.error("zk创建节点失败");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //注册，就是写一个节点数据进去
    public void register(String data) {
        if (null != data) {
            ZooKeeper zk = connectServer();
            if (null != zk) {
                createNode(zk, data);
            }
        }
    }
}
