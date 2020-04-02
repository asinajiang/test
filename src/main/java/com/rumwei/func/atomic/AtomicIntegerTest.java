package com.rumwei.func.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

    private static Integer m = 0;
    private static CountDownLatch latch = new CountDownLatch(1);
    private static AtomicInteger n = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable mRun = new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await();
                } catch (InterruptedException e) {}
                m++;
            }
        };
        Runnable nRun = new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await();
                } catch (InterruptedException e) {}
                n.getAndIncrement();
            }
        };
        for (int i=0; i<30; i++){
            new Thread(mRun).start();
        }
        for (int i=0; i<30; i++){
            new Thread(mRun).start();
        }
        latch.countDown();
        System.out.println(m);
        System.out.println(n);
    }
}
