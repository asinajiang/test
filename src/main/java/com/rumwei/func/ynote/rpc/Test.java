package com.rumwei.func.ynote.rpc;

import com.rumwei.func.ynote.rpc.common.InterfaceContract;
import com.rumwei.func.ynote.rpc.consumer.CBootstrap;
//mark 20200309001
public class Test {
    public static void main(String[] args) {
        CBootstrap consumer = new CBootstrap();
        InterfaceContract service = (InterfaceContract) consumer.getBean(InterfaceContract.class, CBootstrap.protocolPrefix);
        //通过代理对象调用provider提供对方法
        String response = service.reverse("123456789");
        System.out.println(response);
    }
}
