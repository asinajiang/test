package com.rumwei.func.entity;

import com.rumwei.util.ObjectUtilGW;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EntityMain {
    public static void main(String[] args) throws Exception {
        List<DeviceStatus> list = new ArrayList<>();
        for (int i=0; i<2; i++){
            DeviceStatus status = new DeviceStatus();
            status.setDeviceCode("deviceCode"+i);
            status.setProp("prop"+i);
            status.setValue("value"+i);
            status.setModifyTime(new Timestamp(System.currentTimeMillis()));
            list.add(status);
        }
        String res = ObjectUtilGW.ObjectToJsonString(list);
        System.out.println(res);
    }
}
