package com.rumwei.func.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class OriginProxy implements InvocationHandler {
    private Object target;

    public  OriginProxy(OriginInterface target){
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("begin invoke method.");

        Object result = method.invoke(target, args);

        System.out.println("end invoke method.");

        return result;
    }
}
