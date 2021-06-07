package com.rumwei.func.normal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NormalTest {
    public static void main(String[] args){
        for (int index = 0; index < 10; index++) {
            log.info("print {}", index);
        }
    }
}
