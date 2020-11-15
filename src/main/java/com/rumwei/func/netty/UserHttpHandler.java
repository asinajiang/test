package com.rumwei.func.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class UserHttpHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        System.out.println("entered userHttpHandler");
        if (httpObject instanceof HttpRequest) {
            ByteBuf buf = Unpooled.copiedBuffer("Hello word", CharsetUtil.UTF_8);
            FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
            res.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            res.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
            ctx.writeAndFlush(res);
        }
    }
}
