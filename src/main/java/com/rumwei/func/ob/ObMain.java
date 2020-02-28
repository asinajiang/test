package com.rumwei.func.ob;

import java.util.Observable;
import java.util.Observer;

public class ObMain extends Observable {
    public static void main(String[] args) {
        ObMain obMain = new ObMain();
        obMain.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        });
        obMain.setChanged();
        System.out.println("before");
        obMain.notifyObservers("args");
        System.out.println("after");
    }
}
