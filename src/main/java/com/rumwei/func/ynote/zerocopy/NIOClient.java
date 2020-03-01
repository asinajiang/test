package com.rumwei.func.ynote.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
public class NIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String fileName = "/Users/guwei/Downloads/dataNIO.pdf";
        //得到一个文件channel
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        long startTime = System.currentTimeMillis();
        //在Linux下，一个transferTo方法就可以完成整个文件的发送
        //在windows下，一次transferTo方法的调用只能发送最多8m文件，因此当文件超过该大小，需要分段传输，利用该方法的前两个入参来完成分段传输
        //transferTo底层使用零拷贝
        long count = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送总字节数："+count+"耗时："+(System.currentTimeMillis()-startTime));
        fileChannel.close();
    }
}
