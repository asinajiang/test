package com.rumwei.func.lock;

public class LockMain {
    public static void main(String[] args) throws InterruptedException {
        LockService lockService = new LockService();
        MyThread myThread = new MyThread(lockService);
        myThread.start();
        Thread.sleep(4000);
        lockService.signal();
    }
}
