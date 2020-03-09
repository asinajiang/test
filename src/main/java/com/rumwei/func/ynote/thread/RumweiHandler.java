package com.rumwei.func.ynote.thread;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
/**
* @ChannelHandler.Sharable 让用户来标识该ChannelHandler的某个实例可以被多次添加到多个ChannelPipelines中，而且不会出现竞争条件。
 * 一般如果没有标识@Shareable，在添加到到一个pipeline中时，最好每次都创建一个新的handler实例，因为该Handler的开发者没有告知该Handler
 * 是否在对象级别是线程安全的
* */
@ChannelHandler.Sharable
public class RumweiHandler extends ChannelInboundHandlerAdapter {
    //首先必须是静态的，需要被所有RumweiHandler对象共享。本Handler中的耗时任务都可以交给该线程池来完成
    static final EventExecutorGroup group = new DefaultEventExecutorGroup(16);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        group.submit(()->{
            System.out.println(msg);
//            Object o = JDBC.call();
//            ctx.writeAndFlush(o); //交给IO线程执行
        });
    }
}
