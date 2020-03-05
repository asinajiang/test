package com.rumwei.func.ynote.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;
//mark 20200305001
public class RumweiServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) //在bossGroup增加一个日志处理器(Netty提供的)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            //加入一个Netty提供的用来做状态检测的处理器
                            //入参 3--表示3s没有读,就会发送一个心跳检测包,检测是否还是连接的状态,对应一个IdleStateEvent事件
                            //     5--表示5s没有写,就会发送一个心跳检测包,检测是否还是连接的状态,对应一个IdleStateEvent事件
                            //     7--表示7s没有读写,就会发送一个心跳检测包,检测是否还是连接的状态,对应一个IdleStateEvent事件
                            //当IdleStateEvent事件发生后，就会传递给管道的下一个handler的userEventTriggered方法去处理
                            sc.pipeline().addLast(new IdleStateHandler(5,10,20, TimeUnit.SECONDS));
                            sc.pipeline().addLast(new RumweiStateHandler()); //上面的IdleStateEvent事件会传给下一个处理器，需要自定义
                        }
                    });
            ChannelFuture future = server.bind(7000).sync();
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
