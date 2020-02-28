package com.rumwei.func.xishu;

public class XISHUMain {
    public static void main(String[] args) {
        JUZHEN juzhen = new JUZHEN(100000,200000);
        juzhen.set(9000,7888,3.4f);
        juzhen.set(9001,7887,7.8f);
        System.out.println(juzhen.get(9001,7887));

    }

}
