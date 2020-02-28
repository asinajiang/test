package com.rumwei.func.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class BIOServer {
    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true){
            //监听，等待客户端连接,一旦有客户端连接了,就会得到一个socket,即下方的accept()方法就会返回,否则就会一直阻塞着
            final Socket socket = serverSocket.accept();
            System.out.println("连接到了一个客户端");
            //来了一个客户端之后就启动一个线程来与这个客户端进行通信
            threadPool.execute(() -> {
                //可以与客户端进行通信了
                handler(socket);
            });
        }
    }
    //handler方法，负责与客户端通信并处理相关的业务逻辑
    static void handler(Socket socket){
        System.out.println("处理该客户端的服务器线程name："+Thread.currentThread().getName());
        byte[] bytes = new byte[1024]; //用来存放客户端发来的数据
        //通过socket来获取输入流
        try{
            InputStream inputStream = socket.getInputStream();
            //上面的bytes容量有限，因此需要循环的读取客户端发来的数据，一次1k数据
            while (true) {
                //阻塞等待用户输入，当客户端有数据发送过来后，read方法会读取数据放入bytes中，然后重新进入阻塞状态等待用户输入
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes,0,read));
                }else { //客户端关闭
                    System.out.println("客户端关闭");
                    break;
                }
            }
        }catch(IOException e) {
        }finally {
            try{
                //关闭和client的连接
                socket.close();
            }catch(IOException e){}

        }
    }
}
