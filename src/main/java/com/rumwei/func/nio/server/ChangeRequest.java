package com.rumwei.func.nio.server;

import java.nio.channels.SocketChannel;

public class ChangeRequest {

    public static final int REGISTER = 1; //注册
    public static final int CHANGEOPS = 2; //更改操作
    public SocketChannel socketChannel;
    public int type;
    public int op;

    public ChangeRequest(SocketChannel socketChannel, int type, int op) {
        this.socketChannel = socketChannel;
        this.type = type;
        this.op = op;
    }
}
