package com.rumwei.func.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

public class BizClient {
    private static ZooKeeper zkClient;
    public static void main(String[] args) throws Exception {
        //1.获取zookeeper集群连接
        String connectStr = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183"; //zk集群配置
        int sessionTimeout = 2000; //2000ms
        final List<String> hosts = new ArrayList<>(); //存储业务服务器信息
        zkClient = new ZooKeeper(connectStr, sessionTimeout, watchedEvent -> {
            if (watchedEvent.getType() != Watcher.Event.EventType.NodeDeleted) {
                //当业务服务器发生上下线，则动态更新hosts数据
                try {
                    getHosts(zkClient, hosts);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//        zkClient = new ZooKeeper(connectStr,sessionTimeout,null);

        //2.获取业务服务器信息
        getHosts(zkClient,hosts);
        Thread.sleep(Long.MAX_VALUE);
    }

    private static void getHosts(ZooKeeper zkClient, List<String> hosts) throws Exception {
        hosts.clear();
        List<String> servers = zkClient.getChildren("/servers",true);
        servers.forEach(p->{
            try {
                byte[] data = zkClient.getData("/servers/" + p, watchedEvent -> {
                    //当业务服务器发生下线，则动态更新hosts数据
                    if (watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted){
                        try{
                            getHosts(zkClient,hosts);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }, null);
                hosts.add(new String(data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("************get servers start*************");
        hosts.forEach(p->System.out.println(p));
        System.out.println("************get servers end*************");
    }


}
