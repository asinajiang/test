package com.rumwei.func.json;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import java.util.Arrays;

public class JsonTest {
    public static void main(String[] args) {
        String str = "{\"profile\": {\"device_category\": \"该设备类型的类型名称\",\"vertion\": \"1.0\"},\"properties\": [\"电量\",\"水位\"],\"services\": []}";
        JSONObject json = new JSONObject(str);
        Object properties = json.get("properties");
        System.out.println("dd");
    }
}
