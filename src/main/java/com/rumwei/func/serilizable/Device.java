package com.rumwei.func.serilizable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@Builder
public class Device {
    private String name;
    private int age;
    private Map<String, BigDecimal> attachMap;
}
