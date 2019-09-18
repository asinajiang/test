package com.rumwei.func.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
public class AnnotationTest {
    public static void main(String[] args){
        HuBei huBei = new HuBei("hubei",34);
        ShangHai shangHai = new ShangHai(2,12200);
        Country country = new Country(huBei,shangHai);
        Field[] fields = country.getClass().getDeclaredFields();
        for (Field field : fields){
            boolean flag = field.isAccessible();
            Object oo = null;
            try{
                field.setAccessible(true);
                oo = field.get(country);
            }catch(Exception e){
                e.printStackTrace();
            }
            field.setAccessible(flag);

            Action action = field.getAnnotation(Action.class);
            Annotation annotation = field.getDeclaredAnnotations()[0];
            Action action1 = (Action)annotation;
            if (action.type() == OperationType.INSERT){
                OperationService.insert(oo);
            }else if (action.type() == OperationType.UPDATE){
                OperationService.update(oo);
            }
        }
    }
}
