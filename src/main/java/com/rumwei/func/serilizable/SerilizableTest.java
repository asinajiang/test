package com.rumwei.func.serilizable;

import com.rumwei.util.ObjectUtilGW;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SerilizableTest {
    public static void main(String[] args) throws Exception {
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("cn",BigDecimal.ONE);
        map.put("地点",BigDecimal.TEN);
        Device device = Device.builder().name("honghong").age(12).attachMap(map).build();
        String res = ObjectUtilGW.ObjectToJsonString(device);
        System.out.println(res);

        System.out.println(System.getProperty("line.separator"));


        Person person = Person.builder().age(11).sex(2).name("Person2").build();
        People people = People.builder().addition("Additional").person(person).build();


        try {
            String addition = people.getAddition();
            ObjectOutputStream os = new ObjectOutputStream(
                    new FileOutputStream("/Users/guwei/Downloads/test.txt"));




            os.writeObject(people); // 将User对象写进文件
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
