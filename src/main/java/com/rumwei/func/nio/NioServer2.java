package com.rumwei.func.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer2 {
    public static void main(String[] args) throws Exception{
        int[] ports = {5000, 5001, 5002, 5003, 5004};
        Selector selector = Selector.open();
        for (int port : ports) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口:" + port);
        }

        while (true) {
            int number = selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            System.out.println("就绪的selectionKey：" + selectedKeys);
            Iterator<SelectionKey> iter = selectedKeys.iterator(); //处理就绪的selectionKey
            while (iter.hasNext()) {
                SelectionKey selectionKey = iter.next();
                if (selectionKey.isAcceptable()) { //如果注册了Accept事件，且已经就绪(即TCP连接建立好了)
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //连接建立好之后，下一步就是读写了，也是阻塞的，因此再次注册到selector中，等待下次select操作检查是否ready
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int byteNum = 0;
                    while (true) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        byteBuffer.clear();
                        int read = socketChannel.read(byteBuffer);
                        if (read <= 0) { //表明读取完了
                            break;
                        }
                        byteNum += read;
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer); //将来自客户端的消息回写给客户端
                    }
                    System.out.println("一共读取了" + byteNum + "个字节");
                }
                iter.remove();
            }
        }
    }
}
