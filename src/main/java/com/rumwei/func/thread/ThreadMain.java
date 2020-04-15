package com.rumwei.func.thread;


import java.nio.channels.Selector;

public class ThreadMain {
    public static void main(String[] args) {



        Thread t1 = new Thread(()->{
            int count = 20;
            while (count-- > 0){
                System.out.println("DiDaInT1");
                long currentTime = System.currentTimeMillis();
                while (true){
                    if(System.currentTimeMillis()-currentTime > 1000){
                        break;
                    }
                }
            }
        });
        Thread t2 = new Thread(()->{
            try{
                Thread.sleep(7000);
            }catch(Exception e){}
            System.out.println("is going to interrupt t1");
            t1.interrupt();
        });
        t1.start();
        t2.start();
        try{
            t1.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        int count = 20;
        while (count-- > 0){
            System.out.println("DiDaInT2");
            long currentTime = System.currentTimeMillis();
            while (true){
                if(System.currentTimeMillis()-currentTime > 1000){
                    break;
                }
            }
        }




//        ServiceThread service = new ServiceThread() {
//            @Override
//            public String getServiceName() {
//                return "service";
//            }
//
//            @Override
//            public void run() {
//                for (int i=0; i<1000; i++) {
//                    if (!isStopped()) {
//                        System.out.println(i + Thread.currentThread().getName());
//                        try {
//                            Thread.sleep(1000);
//                        } catch (Exception e) {
//                        }
//                    }
//                }
//            }
//        };
//        service.start();
//
//        try{
//            Thread.sleep(10*1000);
//        }catch(Exception e){}
//        service.shutdown(true);
//        try{
//            Thread.sleep(10*1000);
//        }catch(Exception e){}
//        service.shutdown(true);


    }
}
