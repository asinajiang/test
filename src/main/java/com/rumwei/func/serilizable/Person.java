package com.rumwei.func.serilizable;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Person implements Serializable {
    transient int age;
    transient int sex;
    transient String name;
}
