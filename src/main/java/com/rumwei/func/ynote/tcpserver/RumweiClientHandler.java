package com.rumwei.func.ynote.tcpserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
//本类功能：发消息，收消息
//pipeline中的Handler类型约束--ChannelHandler接口
public class RumweiClientHandler extends ChannelInboundHandlerAdapter {
    //当通道就绪时就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client's context is "+ctx); //客户端的上下文
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello server, I am client.", CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf);
    }
    //当通道有数据可读时触发该方法。此处有通道就表示服务器有返回的信息了
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("client says: message from server is "+buf.toString(CharsetUtil.UTF_8));
        System.out.println("client says: server's address is "+ctx.channel().remoteAddress());
    }
    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
