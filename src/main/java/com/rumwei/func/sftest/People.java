package com.rumwei.func.sftest;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class People {

    /*
    * @author nianling
    * */
    private int age;
    private String name;

    public People(int age, String name) {
        this.age = age;
        this.name = name;
    }
}
