package com.rumwei.func.thread;


import java.nio.channels.Selector;

public class ThreadMain {
    public static void main(String[] args) {

        ServiceThread service = new ServiceThread() {
            @Override
            public String getServiceName() {
                return "service";
            }

            @Override
            public void run() {
                for (int i=0; i<1000; i++) {
                    if (!isStopped()) {
                        System.out.println(i + Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                }
            }
        };
        service.start();

        try{
            Thread.sleep(10*1000);
        }catch(Exception e){}
        service.shutdown(true);
//        try{
//            Thread.sleep(10*1000);
//        }catch(Exception e){}
//        service.shutdown(true);


    }
}
