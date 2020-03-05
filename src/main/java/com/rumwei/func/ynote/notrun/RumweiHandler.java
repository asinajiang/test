package com.rumwei.func.ynote.notrun;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
//用户自定义的Handler
public class RumweiHandler extends SimpleChannelInboundHandler<String> {
    //入参为执行channelGroup中逻辑的线程
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //当向pipeline中添加handler时触发
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //向channelGroup中的所有channel广播消息。此处入参能直接写入String，是因为上方泛型为String，因此必然引入了String的编解码器
        channelGroup.writeAndFlush("广播消息");
        channelGroup.writeAndFlush("部分广播消息", channel1 -> { //真实类型ChannelMatcher类
            return channel1.remoteAddress().equals("xxx"); //过滤条件
        }); //向channelGroup中的部分channel广播消息
        channelGroup.add(channel); //将Channel交由channelGroup管理
        //注意：移除管理不需要手动执行，channelGroup会自动移除失效的Channel
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
    }
}
