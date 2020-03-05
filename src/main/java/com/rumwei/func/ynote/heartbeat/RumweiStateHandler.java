package com.rumwei.func.ynote.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import java.time.LocalDateTime;
public class RumweiStateHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception { //evt就是事件
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            //判断具体的状态事件
            String info = null;
            switch (event.state()){
                case READER_IDLE: {info = "读空闲";break;}
                case WRITER_IDLE: {info = "写空闲";break;}
                case ALL_IDLE: {info = "读写空闲";break;}
            }
            System.out.println("["+ LocalDateTime.now() +"]"+"客户端"+ctx.channel().remoteAddress()+"发生超时事件--"+info);
        }
    }
}
