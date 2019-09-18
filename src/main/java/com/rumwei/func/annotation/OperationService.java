package com.rumwei.func.annotation;

public class OperationService {

    public static void insert(Object o){
        if (o instanceof HuBei){
            System.out.println("insert hubei "+((HuBei)o).name);
        }else if (o instanceof ShangHai){
            System.out.println("insert shanghai "+((ShangHai)o).area);
        }else {
            System.out.println("insert error");
        }
    }

    public static void update(Object o){
        if (o instanceof HuBei){
            System.out.println("update hubei "+((HuBei)o).name);
        }else if (o instanceof ShangHai){
            System.out.println("update shanghai "+((ShangHai)o).area);
        }else {
            System.out.println("update error");
        }
    }
}
