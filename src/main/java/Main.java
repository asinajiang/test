

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {




    static class Person{
        public byte[] placeHolder = new byte[64*1024];
    }

    public static void fillHeap(int num) throws InterruptedException {


    }

    public static void main(String[] args) {
        String mi = "name";
        System.out.println(mi);
        System.out.println(mi);
        System.out.println(mi);

        System.out.println("start");




        //moveMouse();



    }


    private static void moveMouse(){
        try{
            Robot robot = new Robot();
            robot.setAutoDelay(5);
            robot.setAutoWaitForIdle(true);

            while (true){
                robot.mouseMove(100,100);
                robot.delay(60*1000);
                robot.mouseMove(300,300);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void test(){

    }
}
