package com.rumwei.func.jdk;

public class ThreadLocalTest {
    public static void main(String[] args) {
        System.out.println("start");
        Thread thread = new Thread(() -> {
            ThreadLocal<String> s = new ThreadLocal<>();
            ThreadLocal<Integer> i = new ThreadLocal<>();
            s.set("hallo");
            i.set(78);
            System.out.println(s.get());
        });
        thread.start();
        System.out.println("dd");

    }
}
