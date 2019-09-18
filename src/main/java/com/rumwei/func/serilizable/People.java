package com.rumwei.func.serilizable;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Builder
@Data
public class People implements Serializable {
    Person person;
    String addition;
}
