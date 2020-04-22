package com.rumwei.func.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class DeviceStatus {
    private String deviceCode;
    private String prop;
    private String value;
    private Timestamp modifyTime;


}
