package com.rumwei.func.ynote.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
* Mark:20200301001
* */
//传统IO服务器
public class OldIOServer {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            try{
                byte[] byteArray = new byte[4096];
                while (true) {
                    int read = dataInputStream.read(byteArray,0,byteArray.length);
                    if (read == -1) break;

                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
