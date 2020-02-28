package com.rumwei.func.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        //创建服务端socket
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();
        //获取来自客户端的内容
        InputStream in = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        while (len > 0){
            String text = new String(buffer,0,len);
            System.out.println("server says: The data from client is "+text);
            len = in.read(buffer);
        }
        socket.close();
        serverSocket.close();
    }
}
