package com.rumwei.func.nio.server;

import java.nio.channels.SocketChannel;

public class RequestSegmentHeader {

    SocketChannel client;
    byte[] data;
    public RequestSegmentHeader(SocketChannel client, byte[] data) {
        this.client = client;
        this.data = data;
    }
}
