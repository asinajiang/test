package com.rumwei.func.tcp;

import com.rumwei.enums.DateType;
import com.rumwei.util.CalendarUtilGW;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {

        System.out.println(CalendarUtilGW.calendarToString(Calendar.getInstance(), DateType.YMDHMSSSS));
        System.out.println("start1");
        //创建client socket服务
        try{
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("localhost",8080),2000);
            OutputStream outputStream = socket.getOutputStream();
            //往服务端发送的数据
            int num = 10;
            while(num-- > 0){
                System.out.println("start");
                outputStream.write(("TCP is coming!"+ UUID.randomUUID().toString()).getBytes());
//            socket.close();
                System.out.println("client says: I have send the message!");
            }
        }catch(Exception e1){
            System.out.println(CalendarUtilGW.calendarToString(Calendar.getInstance(), DateType.YMDHMSSSS));
            e1.printStackTrace();
        }



    }

}
