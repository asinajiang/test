package com.rumwei.func.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
public class GroupChatClient {
    private final String HOST = "127.0.0.1"; //服务器ip
    private final int PORT = 6667; //服务器端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;
    //完成初始化工作
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false); //设置非阻塞
        socketChannel.register(selector, SelectionKey.OP_READ); //将socketChannel注册到selector
        username = socketChannel.getLocalAddress().toString(); //名字就用客户端ip加端口
        System.out.println("客户端"+username+" is ok~");
    }
    //向服务器发送消息
    public void sendInfo(String msg) {
        msg = username + " 说：" + msg;
        try{
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //处理服务器端转发过来的消息
    public void readInfo() {
        try {
            int read = selector.select(); //会一直阻塞，直到有通道准备好
            if (read > 0){ //有准备好的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()){ //处理来自服务器端的消息
                        SocketChannel sc = (SocketChannel)key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        sc.read(buffer); //将通道中的数据读到buffer中
                        String msgFromServer = new String(buffer.array());
                        System.out.println("[收到服务器转发的消息:]"+msgFromServer.trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        //构造客户端并利用客户端的构造函数逻辑完成初始化工作
        GroupChatClient client = new GroupChatClient();
        //启动一个线程每隔3s读取一次来自服务器的消息
        new Thread(()->{
            while (true) {
                client.readInfo(); //每隔一段时间读取一次
                try{
                    Thread.sleep(3000);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        //启动另外一个线程来向服务器端发送数据
        Scanner scanner = new Scanner(System.in); //键盘输入
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            client.sendInfo(msg);
        }
    }
}
