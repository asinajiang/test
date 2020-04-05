package com.rumwei.func.serilizable;

import com.rumwei.util.ObjectUtilGW;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class SerilizableTest {
    public static void main(String[] args) throws Exception {
        People people = People.builder().attachName("name").attachPrice(BigDecimal.TEN).build();
        People people1 = People.builder().attachName("name").attachPrice(BigDecimal.TEN).build();
        List<People> peoples = new ArrayList<>();
        peoples.add(people);
        peoples.add(people1);
        Device device = Device.builder().name("honghong").age(12).attaches(peoples).list(Arrays.asList(2,3,5,6)).build();
        String res = ObjectUtilGW.ObjectToJsonString(device);
        System.out.println(res);

        System.out.println(System.getProperty("line.separator"));


        Person person = Person.builder().age(11).sex(2).name("Person2").build();



        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(
                    "/Users/guwei/Downloads/test.txt"));
            People p1 = (People) is.readObject(); // 从流中读取User的数据
            is.close();
            System.out.println("dd");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void test(){

    }
}
