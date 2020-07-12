package com.rumwei.func.thread;
public class JoinAndInterrupt {
    /**
     * 涉及3个线程：M-Main线程，A-A线程，B-B线程
     * 正常由M线程来启动A，B线程。
     * 在A线程中，每秒打印一次ATik，一共打印20次，只是在第8s时，B线程会join进来
     * 在B线程中，也是每秒打印一次BTik，一共打印20次
     * 正常情况下，A线程会等待B线程执行完，再执行自己的逻辑，若此时我们不想A线程继续等待，则可以在M线程中，对A线程进行打断操作
     * */
    public static void main(String[] args) {
//        testA();
        testB();
    }

    private static void testB() {
        Thread B = new Thread(()->{
            for (int i=0; i<3; i++){
                System.out.println("BTik-"+i);
            }
            try{
                Thread.sleep(5000);
            }catch(Exception e){
                e.printStackTrace();
            }
            for (int i=3; i<10; i++){
                System.out.println("BTick-"+i);
            }
        });
        Thread A = new Thread(()->{
            int count = 20;
            while (count-- > 0){
                if(count == 11){
                    B.start();
                    try {
                        B.join(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("ATik");

            }
        });
        A.start();
        try{
            Thread.sleep(16*1000);
        }catch(Exception e){}
        //在t=16s时对A线程进行打断
        //打印结果：打印8次ATik，8次BTik，上面R29行将抛出InterruptedException异常，然后ATik与BTik会交替打印12次，结束
        //注意虽然执行的是A.interrupt，但是是B.join方法抛异常，即让A放弃继续等待B线程的策略

    }


    private static void testA(){
        Thread B = new Thread(()->{
            int count = 20;
            while (count-- > 0){
                System.out.println("BTik");
                long currentTime = System.currentTimeMillis();
                while (true){
                    if(System.currentTimeMillis()-currentTime > 1000){
                        break;
                    }
                }
            }
        });
        Thread A = new Thread(()->{
            int count = 20;
            while (count-- > 0){
                if(count == 11){
                    B.start();
                    try {
                        B.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("ATik");
                long currentTime = System.currentTimeMillis();
                while (true){
                    if(System.currentTimeMillis()-currentTime > 1000){
                        break;
                    }
                }
            }
        });
        A.start();
        try{
            Thread.sleep(16*1000);
        }catch(Exception e){}
        //在t=16s时对A线程进行打断
        //打印结果：打印8次ATik，8次BTik，上面R29行将抛出InterruptedException异常，然后ATik与BTik会交替打印12次，结束
        //注意虽然执行的是A.interrupt，但是是B.join方法抛异常，即让A放弃继续等待B线程的策略
        A.interrupt();
        //如果不执行A.interrupt()，那么打印结果就是打印8次ATik，20次BTik，再打印12次ATik，结束
    }
}
//interview-20200415-001
