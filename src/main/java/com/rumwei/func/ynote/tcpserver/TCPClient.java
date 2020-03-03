package com.rumwei.func.ynote.tcpserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
public class TCPClient {
    public static void main(String[] args) throws Exception{
        //客户端需要一个事件循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            //创建一个客户端的启动对象，辅助客户端的启动以及初始化工作
            //服务端使用ServerBootStrap，客户端使用BootStrap
            Bootstrap bootstrap = new Bootstrap();
            //给bootstrap设置初始化参数
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class) //设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new RumweiClientHandler());
                        }
                    });
            //启动客户端去连接服务器端
            ChannelFuture channelFuture = bootstrap.connect("localhost", 6668).sync();
            channelFuture.channel().closeFuture().sync(); //同server端
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
