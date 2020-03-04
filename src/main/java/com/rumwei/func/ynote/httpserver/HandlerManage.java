package com.rumwei.func.ynote.httpserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
public class HandlerManage extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //加入Netty已经提供好的Http的编解码器-HttpServerCodec--[coder,decoder]
        socketChannel.pipeline().addLast("httpCodec",new HttpServerCodec());
        //加入一个自定义的Handler
        socketChannel.pipeline().addLast("myHandler",new RumWeiHandler());
    }
}
