package com.rumwei.func.ynote.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
public class OldIOClient {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost",7001);
        String fileName = "/Users/guwei/Downloads/data.pdf";
        InputStream inputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];
        long read;
        long total = 0;
        long startTime = System.currentTimeMillis();
        while ((read = inputStream.read(buffer)) >= 0){
            total += read;
            dataOutputStream.write(buffer);
        }
        System.out.println("发送总字节数："+total+"耗时："+(System.currentTimeMillis()-startTime));
        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
