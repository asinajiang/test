package com.rumwei.func.ynote.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
//此处TextWebSocketFrame类型，表示文本帧
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        System.out.println("服务器收到消息 "+msg.text()); //收取来自客户端的消息
        //回复浏览器
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器已收到来自浏览器的消息："+msg.text()));
    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("handlerAdded被调用了，相应的channelID为:"+ctx.channel().id().asLongText());
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved被调用了，相应的channelID为:"+ctx.channel().id().asLongText());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生，异常原因为"+cause.getMessage());
        ctx.close();
    }
}
