package com.rumwei.func.normal;

public class NormalTest {

    private static int i = 100;

    public static void main(String[] args){
        NormalTest nt1 = new NormalTest();
        nt1.i++;
        NormalTest nt2 = new NormalTest();
        nt2.i++;
        nt1 = new NormalTest();
        nt1.i++;
        NormalTest.i--;
        System.out.println("i="+i);
    }

}
