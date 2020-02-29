package com.rumwei.func.nio;

import java.io.IOException;
        import java.net.InetSocketAddress;
        import java.nio.ByteBuffer;
        import java.nio.channels.SelectionKey;
        import java.nio.channels.Selector;
        import java.nio.channels.ServerSocketChannel;
        import java.nio.channels.SocketChannel;
        import java.util.Iterator;
        import java.util.Set;
public class NIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        //得到一个Selector对象
        Selector selector = Selector.open();
        //为server绑定端口6666
        server.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        server.configureBlocking(false);
        //将server注册到selector中，设置关心到事件为OP_ACCEPT事件
        server.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while (true) {
            System.out.println(selector.keys().size());
            if (selector.select(4000) == 0){ //说明没有事件发生，即没挑选到已经ready的Channel
                System.out.println("服务器等待了4s，没有事件发生");
                continue;
            }
            //说明有通道发生了事件需要处理
            Set<SelectionKey> readyChannels = selector.selectedKeys(); //获取这些可以处理的Channel
            Iterator<SelectionKey> scan = readyChannels.iterator();
            while (scan.hasNext()){
                SelectionKey ele = scan.next();
                //判断ele的事件类型并做相应地处理
                if (ele.isAcceptable()) { //内部实现(readyOps() & OP_ACCEPT) != 0
                    //说明是accept事件，有新客户端连接
                    //1.为该客户端生成对应的与之交互的SocketChannel
                    SocketChannel socketChannel = ((ServerSocketChannel) ele.channel()).accept();
                    //将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel注册到selector中.同时为该socketChannel绑定一个对应的Buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (ele.isReadable()) { //发生OP_READ
                    SocketChannel socketChannel = (SocketChannel) ele.channel();
                    //获取到该socketChannel关联的Buffer
                    ByteBuffer buffer = (ByteBuffer) ele.attachment();
                    //将当前socketChannel内容读取到buffer中去
                    int read = socketChannel.read(buffer);
                    if (read > 0) {
                        System.out.println("data from client is " + new String(buffer.array()));
                    }else {
                        socketChannel.close();
                    }
                }
                //手动从ready SelectionKey集合中移除已经处理过的SelectionKey
                scan.remove();
            }
        }
    }
}
