package com.rumwei.func.ynote.tcpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
//Mark:20200302001
public class TCPServer {
    public static void main(String[] args) throws Exception {
        //创建bossGroup和WorkerGroup,分别对应两个线程组，且启动之后都会一直无限循环
        //bossGroup只处理accept连接请求，workerGroup处理真正的客户端业务
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务端的启动对象，可以辅助完成服务器的启动及初始化工作
            ServerBootstrap bootstrap = new ServerBootstrap();
            //为bootstrap提供启动参数，以链式编程的方式来设置
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) //设置本服务器使用的通道为NioServerSocketChannel
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列等待连接的个数,类似线程池中的任务等待queue
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    //给我们workGroup中的EventLoop(EL)设置Handler处理器，EL在处理Channel时，会用到这些处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() { //设置的Handler可以是Netty已经写好内置的，也可以是自定义的
                        //该Handler作用是初始化通道
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            System.out.println("initChannel thread name:"+Thread.currentThread().getName());
                            socketChannel.pipeline().addLast(new RumweiServerHandler());
                        }
                    });
            System.out.println("server start...");
            ChannelFuture channelFuture = bootstrap.bind(6668).sync(); //启动服务器并绑定端口，返回一个ChannelFuture对象
            channelFuture.addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("server listen port success.");
                }else {
                    System.out.println("server listen port failed.");
                }
            });
            System.out.println("server has started.");
            //对关闭通道进行监听，当出现关闭通道当事件时，就去关闭通道，而不是马上关闭--close in the future
            channelFuture.channel().closeFuture().sync(); //代码会阻塞在这个地方

        } finally {
            //关闭线程池资源(特别是这两线程池还是无限循环逻辑)
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
