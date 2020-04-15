package com.rumwei.func.thread;
/**
 * 注意lock与ThreadA的关系
 * ThreadA.interrupt()的执行会导致lock.wait()方法抛中断异常
 * mark: interview-20200415-002
 * */
public class WaitAndInterrupt {
    public static void main(String[] args) {
        try {
            Object lock = new Object();
            ThreadA threadA = new ThreadA(lock);
            threadA.start();
            /*wait的线程调用interrupt，抛异常，停止线程，也是线程停止的一种方式*/
            Thread.sleep(3000);
            //正常唤醒
//            synchronized (lock) {
//                lock.notify();
//            }
            //中断线程，执行完后下方的lock.wait()将抛出InterruptedException异常
            threadA.interrupt();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

}

class ThreadA extends Thread {
    private Object lock;
    public ThreadA(Object lock) {
        super();
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName()+" begin=" + System.currentTimeMillis());
                lock.wait();
                System.out.println(Thread.currentThread().getName() +" end=" + System.currentTimeMillis());
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
