package com.rumwei.func.nio;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        //举例说明Buffer的使用
        //1.创建一个Buffer,大小为5,即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //2.向Buffer中存放数据
        for (int i=0; i<intBuffer.capacity(); i++){
            intBuffer.put(i+1);
        }
        //--intBuffer.put(6);-- //若超过容量限制,将抛出BufferOverflowException
        //3.从Buffer取数据
        //3.1.转化Buffer的读写模式
        intBuffer.flip();
        //3.2.读取
        System.out.println(intBuffer.get());
        System.out.println(intBuffer.get());
        intBuffer.flip();
        intBuffer.put(6);
//        while (intBuffer.hasRemaining()){
//            int ele = intBuffer.get();
//            System.out.println(ele);
//        }
        intBuffer.flip();
        System.out.println(intBuffer.get());
        System.out.println(intBuffer.get());
        System.out.println(intBuffer.get());
    }
}
