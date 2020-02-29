package com.rumwei.func.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String content = "hello rumwei";
        //创建一个输出流，该输出流最终将会被包装到Channel里去
        FileOutputStream outputStream = new FileOutputStream("/Users/guwei/Downloads/test.txt");
        //通过上outputStream获取对应的FileChannel,FileChannel可以看作对FileOutputStream做了一个上层包装
        FileChannel channel = outputStream.getChannel();
        //创建一个缓冲区 ByteBuffer,形成 内存--byteBuffer--channel--outputStream--文件的数据通道
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将content放入byteBuffer
        byteBuffer.put(content.getBytes());
        //将byteBuffer中的内容写入channel
        byteBuffer.flip();
        channel.write(byteBuffer);
        outputStream.close();//关闭输出流
    }
}
