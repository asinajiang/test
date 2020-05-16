package com.rumwei.func.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class ZookeeperTest {
    public static void main(String[] args) throws Exception {
        String connectStr = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183"; //zk集群配置
        int sessionTimeout = 2000; //2000ms

        ZooKeeper zkClient = new ZooKeeper(connectStr, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
        //创建znode节点
        String path = zkClient.create("/node/sanguo", "liubei".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path); //将打印 /node/sanguo
        //获取子节点，并监听数据变化
        List<String> children = zkClient.getChildren("/", false);
        children.forEach(p->{System.out.println(p);}); //与命令行输入"ls /" 输出的结果一致
        //判断Znode是否存在
        Stat stat = zkClient.exists("/node",false);
        System.out.println(stat == null ? "not exist" : "exist");

        //借助zookeeper集群，业务客户端实时监听业务服务端服务器的上下线
        //业务服务器上线后就去zookeeper的某个目录下新建对应的节点，业务客户端启动后就去getChildren，获得当前在线服务器列表，并且注册监听
        //当服务端机器下线，就会导致zookeeper目录的变化，从而触发通知，zookeeper将通知会下发给业务客户端
    }
}
