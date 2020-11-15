package com.rumwei.func.thread;

import java.util.concurrent.TimeUnit;

public class JoinThread {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int index = 0; index < 10; index++) {
                System.out.println(index);
                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch(Exception ignored){}
            }
        });
        System.out.println("main-1");
        t1.start();
        //不加此行，输出 main-1 main-2 0 1 2 3 4 5 6 7 8 9
        //  加此行，输出 main-1 0 1 2 3 4 5 6 7 8 9 main-2
        t1.join();
        System.out.println("main-2");
    }
}


