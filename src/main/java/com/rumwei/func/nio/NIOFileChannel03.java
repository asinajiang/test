package com.rumwei.func.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("/Users/guwei/Downloads/test.txt");
        FileChannel inputFileChannel = inputStream.getChannel();
        FileOutputStream outputStream = new FileOutputStream("/Users/guwei/Downloads/test1.txt");
        FileChannel outputFileChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) {//循环读取
            int read = inputFileChannel.read(byteBuffer); //***货车装货***
            if (read == -1) { //读取结束
                break;
            }
            //将本次循环  读取到的内容写入outputFileChannel
            byteBuffer.flip();
            outputFileChannel.write(byteBuffer); //***货车卸货***
            byteBuffer.clear();                  //***货车卸货*** 包含了flip相同的效果，因此不需要再执行flip
        }
        //关闭输入输出流
        inputFileChannel.close();
        outputFileChannel.close();
    }
}
