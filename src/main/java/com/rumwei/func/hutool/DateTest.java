package com.rumwei.func.hutool;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class DateTest {
    public static void main(String[] args) {
        Date date = DateUtil.yesterday();
        System.out.println(date);
    }
}
