package com.rumwei.func.ynote.rpc.provider;

import com.rumwei.func.ynote.rpc.common.InterfaceContract;
public class InterfaceImpl implements InterfaceContract {
    @Override
    public String reverse(String input) {
        if (null != input && input.length() > 0) {
            return new StringBuilder(input).reverse().toString();
        }else {
            return "Info from client is null";
        }
    }
}
