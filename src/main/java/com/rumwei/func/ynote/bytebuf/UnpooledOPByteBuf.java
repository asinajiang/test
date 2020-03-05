package com.rumwei.func.ynote.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
//mark 20200304002
public class UnpooledOPByteBuf {
    public static void main(String[] args) {
        //-------------------------------使用1
        //创建一个ByteBuf对象，该对象包含一个byte[10]的数组
        ByteBuf byteBuf = Unpooled.buffer(10);
        for (int i=0; i<10; i++){ //写入
            byteBuf.writeByte(i);
        }
        //在Netty提供的ByteBuf中，不需要像Nio那样进行flip来进行读写转换
        for (int i=0; i<byteBuf.capacity(); i++){ //读出
            System.out.println(byteBuf.readByte());
        }
        //-------------------------------使用2
        //创建一个ByteBuf对象
        ByteBuf byteBuf1 = Unpooled.copiedBuffer("hello,rumwei", CharsetUtil.UTF_8);
        //取出byteBuf1中的内容
        if (byteBuf1.hasArray()){ //有没有给byteBuf1对象分配数组空间，此处已经初始化了，会返回true
            byte[] array = byteBuf1.array();
            String content = new String(array,CharsetUtil.UTF_8); //content将为"hello,rumwei"
            int arrayOffSet = byteBuf1.arrayOffset(); //0此时还没读取
            int readerIndex = byteBuf1.readerIndex(); //0
            int writerIndex = byteBuf1.writerIndex(); //12
            int capacity = byteBuf1.capacity(); //36
            int len = byteBuf1.readableBytes(); //12，可读取的字节数
        }
    }
}
