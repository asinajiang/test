

import com.rumwei.func.Entity;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
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

        //中文提交测试
        String[] arg = {"-h","-c","usr/local/config.xml","-p"};
        Options options = new Options();
        Option op1 = new Option("h", "help", false, "Print help");
        op1.setRequired(false);
        options.addOption(op1);
        Option op2 = new Option("c", "configFile", true, "config file path");
        op2.setRequired(false);
        options.addOption(op2);

        CommandLine commandLine = null;
        CommandLineParser parser = new PosixParser();
        try{
            commandLine = parser.parse(options,arg);
            if (commandLine.hasOption('h')){
                // 若命令行接了-h，则需要执行的逻辑
                System.out.println("handle logic responding h");
            }
            if (commandLine.hasOption('c')){
                String configFilePath = commandLine.getOptionValue('c');
                System.out.println(configFilePath);
            }
            if (commandLine.hasOption('p')){
                System.out.println();
            }
        }catch(Exception e){
            e.printStackTrace();
        }


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
