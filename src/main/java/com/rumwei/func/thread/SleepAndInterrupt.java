package com.rumwei.func.thread;

import com.rumwei.enums.DateType;
import com.rumwei.util.CalendarUtilGW;

import java.util.Calendar;

/**
 * 26行执行完后，第16行将立即抛出异常，被第17行捕获，然后执行第18行，之后继续执行第20行，A线程运行结束。运行结果
 * Thread-0 start
 * java.lang.InterruptedException: sleep interrupted
 * 	at java.lang.Thread.sleep(Native Method)
 * 	at com.rumwei.func.thread.ThreadMain.lambda$main$0(ThreadMain.java:16)
 * 	at java.lang.Thread.run(Thread.java:748)
 * Thread-0 end
 * */
public class SleepAndInterrupt {
    public static void main(String[] args) {
        Thread tt = new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" start "+ CalendarUtilGW.calendarToString(Calendar.getInstance(), DateType.YMDHMSSSS));
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" end "+ CalendarUtilGW.calendarToString(Calendar.getInstance(), DateType.YMDHMSSSS));
            try{
                Thread.sleep(4000);
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" sleep again "+ CalendarUtilGW.calendarToString(Calendar.getInstance(), DateType.YMDHMSSSS));
        });
        tt.start();
        try{
            Thread.sleep(2000);
        }catch(Exception e){}
        tt.interrupt();

//        tt.interrupt();
//        try{
//            Thread.sleep(2000);
//        }catch(Exception e){}
//        tt.start();
    }
}
