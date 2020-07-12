package com.rumwei.func.thread;

public class ObjectNotify {

    public static void main(String[] args) {
        Object o = new Object();
        Thread a = new Thread(() -> {
            for (int i=0; i<=20; i++) {
                System.out.println(i);
                if (i == 9) {
                    synchronized (o) {
                        try{
                            System.out.println(Thread.currentThread().getName()+":entered wait");
                            o.wait();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Thread b = new Thread(() -> {
            try{
                Thread.sleep(3000);
                synchronized (o) {
                    o.notify();
                    System.out.println(Thread.currentThread().getName() + ":have notify");
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        });
        a.start();
        b.start();

    }
}
