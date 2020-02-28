package com.rumwei.func.lock;

public class MyThread extends Thread{
    private LockService lockService;

    public MyThread(LockService lockService) {
        super();
        this.lockService = lockService;
    }

    @Override
    public void run() {
        lockService.await();
    }
}
