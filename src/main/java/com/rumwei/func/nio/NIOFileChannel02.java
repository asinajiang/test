package com.rumwei.func.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {
        //创建对应的输入流，并挂钩对应的文件
        FileInputStream inputStream = new FileInputStream("/Users/guwei/Downloads/test.txt");
        //通过inputStream获取对应的FileChannel
        FileChannel fileChannel = inputStream.getChannel();
        //创建ByteBuffer缓冲区，作为Channel与内存的桥梁。为节约资源，缓冲区大小可以设置为文件中的字符长度
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
        //将fileChannel中的数据读取到缓冲区
        fileChannel.read(byteBuffer);
        //取出byteBuffer中的字节到变量
        String message = new String(byteBuffer.array());
        System.out.println(message);
        //关闭输入流
        inputStream.close();
    }
}
