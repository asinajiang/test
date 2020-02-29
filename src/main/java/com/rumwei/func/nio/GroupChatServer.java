package com.rumwei.func.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667; //服务端监听的端口
    //初始化工作
    public GroupChatServer(){
        try{
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false); //设置非阻塞模式
            listenChannel.register(selector, SelectionKey.OP_ACCEPT); //注册listenChannel到selector
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //处理客户端过来到请求
    public void requestHandle(){
        try{
            while (true) {
                int count = selector.select(10000);
                if (count > 0) { //说明有Channel产生事件
                    //遍历selector中已经ready的SelectionKeys
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey ele = iterator.next();
                        iterator.remove(); //处理完移除掉
                        if (ele.isAcceptable()){ //OP_ACCEPT事件
                            SocketChannel sc = listenChannel.accept(); //通过ele.channel()获取也行
                            sc.configureBlocking(false);
                            sc.register(selector,SelectionKey.OP_READ); //注册到selector中
                            //上线提醒
                            System.out.println(sc.getRemoteAddress()+" 已 上线");
                        }
                        if (ele.isReadable()){ //OP_READ事件，处理来自客户端到消息
                            SocketChannel channel = null;
                            try{
                                channel = (SocketChannel) ele.channel();
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                int read = channel.read(buffer);
                                if (read > 0){
                                    String msg = new String(buffer.array()); //取出客户端消息
                                    System.out.println("服务器收到消息："+msg);
                                    //将取出到消息msg转发给其他客户端(不包括信息来源的客户端)
                                    System.out.println("服务器开始转发消息...");
                                    for (SelectionKey key : selector.keys()){
                                        Channel ch = key.channel();
                                        if (ch instanceof SocketChannel && ch != channel){
                                            //排除ServerSocketChannel和发消息的客户端
                                            SocketChannel targetClient = (SocketChannel)ch;
                                            ByteBuffer infoToSend = ByteBuffer.wrap(msg.getBytes());
                                            targetClient.write(infoToSend);
                                        }

                                    }
                                    System.out.println("服务器转发消息结束！");
                                }else {
                                    System.out.println(channel.getRemoteAddress()+" 下线了");
                                    //取消注册
                                    ele.cancel();
                                    //关闭通道
                                    channel.close();
                                }
                            }catch(IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }else {
                    System.out.println("等待客户端连接中...");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {

        }
    }
    public static void main(String[] args) {
        //构造服务器端并利用服务器端的构造函数逻辑完成初始化工作
        GroupChatServer server = new GroupChatServer();
        server.requestHandle();
    }
}
