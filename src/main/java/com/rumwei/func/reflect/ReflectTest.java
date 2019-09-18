package com.rumwei.func.reflect;

import java.lang.reflect.Field;
public class ReflectTest {
    public static void main(String[] args){
        Hobby hobby = new Hobby("Go");
        Person person = new Person("ya",23,hobby);
        Field[] fields1 = person.getClass().getDeclaredFields();
        Object oo = null;
        for (Field field : fields1){
            boolean flag = field.isAccessible();
            try{
                field.setAccessible(true);
                oo = field.get(person);
                System.out.println("The value of "+field.getName()+" is "+oo.toString());
            }catch(Exception e){
                e.printStackTrace();
            }
            field.setAccessible(flag);
        }
    }
}
