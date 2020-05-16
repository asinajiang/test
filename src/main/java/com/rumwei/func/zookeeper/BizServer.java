package com.rumwei.func.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

public class BizServer {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        //1.连接zookeeper集群
        String connectStr = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183"; //zk集群配置
        int sessionTimeout = 2000; //2000ms

        ZooKeeper zkClient = new ZooKeeper(connectStr, sessionTimeout, watchedEvent -> {
        });

        //2.注册本服务节点
        if (null == zkClient.exists("/servers",false)){
            zkClient.create("/servers","servers".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        //创建序列临时节点与本服务对应
        zkClient.create("/servers/server",args[0].getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(args[0]+" is online");

        //3.业务代码逻辑
        System.out.println();
        Thread.sleep(Long.MAX_VALUE); //模拟web服务，程序不结束退出
    }
}
