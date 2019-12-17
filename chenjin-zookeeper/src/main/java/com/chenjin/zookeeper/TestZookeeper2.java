package com.chenjin.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class TestZookeeper2 {


    public static void main(String[] args) {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry
                (1000, 3);
        SerializableSerializer zkSerializer = new SerializableSerializer();

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString("192.168.100.166:2181").
                sessionTimeoutMs(180000).
                connectionTimeoutMs(180000)
                .authorization("digest", "zookeeper:zookeeper".getBytes()).
                        retryPolicy(retryPolicy).
                        build();
        curatorFramework.start();
        Id id = null;
        ArrayList<ACL> acls=new ArrayList<>();
        try {
            id = new Id("digest", DigestAuthenticationProvider.
                    generateDigest("zookeeper:zookeeper"));

        acls.add(new ACL(ZooDefs.Perms.ALL, id));
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                    .withACL(acls).forPath("/temp1/aaa", zkSerializer.serialize(""));
            System.out.println("成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            curatorFramework.close();
        }

    }

}
