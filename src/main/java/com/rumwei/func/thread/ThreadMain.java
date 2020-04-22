package com.rumwei.func.thread;


import com.rumwei.func.Entity;

import java.nio.channels.Selector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class ThreadMain {
    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(3);
        Future<?> submit = service.submit(() -> {
            System.out.println("task start");
            int i = 67 * 87;
            System.out.println(i);
            System.out.println("task end");
        });
        try{
            submit.get();
        }catch(Exception e){
            e.printStackTrace();
        }

        final Entity o = new Entity();

        Thread thread = new Thread(()->{
            System.out.println(Thread.currentThread().getName()+": start");
            try{
                LockSupport.park();
                LockSupport.park(o);
            }catch(Exception e){
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName()+":after park");
        });
        thread.start();
//        thread.interrupt();
        try{
            Thread.sleep(3000);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (o.equals(LockSupport.getBlocker(thread))){
            System.out.println("laluhuoduo1");
        }
        LockSupport.unpark(thread);
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (o.equals(LockSupport.getBlocker(thread))){
            System.out.println("laluhuoduo2");
        }
        System.out.println("dd");




//        Thread t1 = new Thread(()->{
//            int count = 20;
//            while (count-- > 0){
//                System.out.println("DiDaInT1");
//                long currentTime = System.currentTimeMillis();
//                while (true){
//                    if(System.currentTimeMillis()-currentTime > 1000){
//                        break;
//                    }
//                }
//            }
//        });
//        Thread t2 = new Thread(()->{
//            try{
//                Thread.sleep(7000);
//            }catch(Exception e){}
//            System.out.println("is going to interrupt t1");
//            t1.interrupt();
//        });
//        t1.start();
//        t2.start();
//        try{
//            t1.join();
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        int count = 20;
//        while (count-- > 0){
//            System.out.println("DiDaInT2");
//            long currentTime = System.currentTimeMillis();
//            while (true){
//                if(System.currentTimeMillis()-currentTime > 1000){
//                    break;
//                }
//            }
//        }




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
