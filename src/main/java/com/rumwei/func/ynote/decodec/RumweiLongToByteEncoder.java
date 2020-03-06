package com.rumwei.func.ynote.decodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
public class RumweiLongToByteEncoder extends MessageToByteEncoder<Long> {
    //将要出站的Long型数据转成ByteBuf类型
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeLong(msg);
    }

}
