package com.rumwei.func.ynote.httpserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import java.net.URI;
public class RumWeiHandler extends SimpleChannelInboundHandler<HttpObject> {
    //HttpObject:因为已经加入了Netty提供的Http编解码器，因此用户消息被编码器或者解码器已经封装成了HttpObject对象来做信息传递，
    // 因此该Handler挂钩的对象为HttpObject，作为客户端与服务端通信的数据的封装对象
    //当有读取事件时，就会触发该方法
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        if (httpObject instanceof HttpRequest){
            //对浏览器的favicon请求做过滤(浏览器自带的功能，会在主请求之外，再请求一次图标，postman发出的http请求没有这个问题)
            URI uri = new URI(((HttpRequest)httpObject).uri());
            if ("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了favicon.ico,不做处理");
                return;
            }
            System.out.println("httpObject 类型："+httpObject.getClass());
            System.out.println("client's address:"+ctx.channel().remoteAddress());
            //回复消息给客户端
            ByteBuf content = Unpooled.copiedBuffer("hello, I am server", CharsetUtil.UTF_8);
            //构造一个Http的响应实体并返回给客户端
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            ctx.channel().writeAndFlush(response);
        }
    }
}
