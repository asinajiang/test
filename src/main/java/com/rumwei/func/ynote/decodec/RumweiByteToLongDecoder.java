package com.rumwei.func.ynote.decodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
public class RumweiByteToLongDecoder extends ByteToMessageDecoder {
    //解码入站数据完成后的数据会放到list中传给下一个Handler进行处理
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        //long是8个字节,因此8个字节，8个字节一读
        if (byteBuf.readableBytes() >= 8){
            list.add(byteBuf.readLong());
        }
        System.out.println(list.size());
    }
}
