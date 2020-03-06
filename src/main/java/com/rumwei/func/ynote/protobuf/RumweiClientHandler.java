package com.rumwei.func.ynote.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
public class RumweiClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送一个Student对象到服务器
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(1).setName("小红").build();
        ctx.writeAndFlush(student);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收来自服务器的回复消息
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
