package com.rumwei.func.volatil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class VolatileTest {
    private static boolean isChanged;

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            for (int i=0; i<10000; i++){
                if (isChanged){
                    System.out.println(i);
                    break;
                }
            }

        }).start();
        Thread.sleep(1);
        new Thread(()->{
            isChanged = !isChanged;
        }).start();

    }
}
