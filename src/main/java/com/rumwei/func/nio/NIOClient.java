package com.rumwei.func.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
public class NIOClient {
    public static void main(String[] args) throws IOException {
        //构造客户端的SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞模式
        socketChannel.configureBlocking(false);
        //提供服务器端的ip和端口，以连接服务器端
        InetSocketAddress serverAddress = new InetSocketAddress("127.0.0.1",6666);
        //连接服务器
        if (!socketChannel.connect(serverAddress)) { //该方法不会阻塞
            while(!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作...");
            }
        }
        //说明连接成功了,开始发送数据
        String content = "hello rumwei";
        //wrap a byte array into a buffer
        ByteBuffer bufferToSend = ByteBuffer.wrap(content.getBytes());
        //发送数据,将bufferToSend的数据写入socketChannel通道
        System.out.println("send");
        socketChannel.write(bufferToSend);
        System.out.println("data send over");
        System.in.read();
    }
}
