package com.rumwei.func.ynote.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
public class RumweiServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取从客户端发送过来的Student对象
        if (msg instanceof StudentPOJO.Student){
            StudentPOJO.Student student = (StudentPOJO.Student) msg;
            System.out.println("客户端发送的Student信息：");
            System.out.print("id:"+student.getId()+";name:"+student.getName());
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
