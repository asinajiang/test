package com.rumwei.func.serilizable;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Data
public class People implements Serializable {
    private String attachName;
    private BigDecimal attachPrice;
}
