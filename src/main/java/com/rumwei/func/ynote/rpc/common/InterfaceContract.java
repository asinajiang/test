package com.rumwei.func.ynote.rpc.common;

//远程调用契约，消费者和提供者的应用都需要通过包引入该接口，因此处是
//在同一个工程里，因此可以省去引入步骤
public interface InterfaceContract {
    String reverse(String input);
}
