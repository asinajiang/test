package com.rumwei.func.ynote.tcpserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

//本类功能：收消息，发消息
//pipeline中的Handler类型约束--ChannelHandler接口
public class RumweiServerHandler extends ChannelInboundHandlerAdapter {
    //当通道有数据可读时触发该方法。此处表示客户端有数据发送过来了
    //@Param ctx：上下文对象，包含了管道pipeline，通道channel，地址
    //@Param msg：就是客户端发送过来的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server context is "+ctx); //打印出上下文
        //将msg转成ByteBuf(Netty提供的，不是NIO的ByteBuffer)
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();
        ByteBuf nettyBuf = (ByteBuf)msg;
        System.out.println("server says: message from client is "+nettyBuf.toString(CharsetUtil.UTF_8));
        System.out.println("server says: client's address is "+ctx.channel().remoteAddress());

        System.out.println("Thread name in channelRead is "+Thread.currentThread().getName());

        ctx.channel().eventLoop().schedule(()->{
//            try{
//                Thread.sleep(3000);
//            }catch(Exception e){}
            System.out.println("Thread name in eventLoop's schedule is "+Thread.currentThread().getName());
        },2, TimeUnit.SECONDS);
        ctx.channel().eventLoop().execute(()->{
            try{
                Thread.sleep(3000);
            }catch(Exception e){}
            System.out.println("Thread name in eventLoop's taskQueue is "+Thread.currentThread().getName());
        });
        ctx.channel().eventLoop().execute(()->{
            try{
                Thread.sleep(4000);
            }catch(Exception e){}
            System.out.println("Thread name in eventLoop's taskQueue is "+Thread.currentThread().getName());
        });
        System.out.println("Thread name in channelRead is "+Thread.currentThread().getName());
    }
    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        try{
//            Thread.sleep(3000);
//        }catch(Exception e){}
        System.out.println("Thread name in channelReadComplete is "+Thread.currentThread().getName());
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello client, I am server.", CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf); //将数据写入缓冲区并刷入通道。一般发送的数据需要进行编码
    }
    //处理异常,一般需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close(); //包含ctx中的通道的关闭
    }
}
