package com.rumwei.func.ynote.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import java.util.Properties;
import java.util.concurrent.Executor;
//mark 20200310
public class NacosTest {
    public static void main(String[] args) throws NacosException, InterruptedException {
        //nacos服务所在的ip与端口
        String serverAddr = "localhost:8848";
        //DataId
        String dataId = "demo.yaml";
        //group
        String group = "DEFAULT_GROUP";
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR,serverAddr);
        properties.setProperty(PropertyKeyConst.USERNAME,"nacos");
        properties.setProperty(PropertyKeyConst.PASSWORD,"nacos");
//        properties.setProperty(PropertyKeyConst.NAMESPACE,"namespaceId"); //可以不指定，默认Public
        ConfigService configService = NacosFactory.createConfigService(properties);
        //获取配置,5000为超时时间
        String content = configService.getConfig(dataId,group,5000);
        System.out.println(content);
        //监听服务端，配置项目发生更改
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }
            @Override
            public void receiveConfigInfo(String s) {
                System.out.println("远端配置发生了更改，更改后的配置内容为:");
                System.out.println(s);
            }
        });
//        while (true){ //为上方的监听
//            Thread.sleep(3000);
//        }
        //发布配置(会直接覆盖原有的配置，因此一般先查询，然后在老配置上append新配置，再写入)
        boolean isPublishOk = configService.publishConfig(dataId,group,"mysql.password: root");
        System.out.println(isPublishOk);
//        //移除配置
        boolean isRemovedOk = configService.removeConfig(dataId,group);
        System.out.println(isRemovedOk);

    }
}
