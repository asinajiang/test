package com.rumwei.func.ynote.rpc.provider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
public class ProviderNettyHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //客户端在调用服务器的api时，需要规定一个协议，此处对该协议进行判断
        //比如要求每次发消息时都必须以某个字段开头
        if (msg.toString().startsWith("remote#service#")) {
            InterfaceImpl provider = new InterfaceImpl();
            String content = msg.toString().substring(15);
            String res = provider.reverse(content); //调用服务
            ctx.writeAndFlush(res);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
