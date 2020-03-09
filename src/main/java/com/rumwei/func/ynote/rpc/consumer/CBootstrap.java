package com.rumwei.func.ynote.rpc.consumer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class CBootstrap {
    private static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static ConsumerNettyHandler consumer;
    private static final String HOST = "localhost";
    private static final int PORT = 7000;
    public static final String protocolPrefix = "remote#service#";
    public void init() throws Exception{
        consumer = new ConsumerNettyHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        client.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new StringDecoder());
                        sc.pipeline().addLast(new StringEncoder());
                        sc.pipeline().addLast(consumer);
                    }
                });
        ChannelFuture future = client.connect(HOST, PORT).sync();
    }
    //使用代理模式获取一个代理对象
    public Object getBean(final Class<?> serviceClass, final String protocolPrefix){
        return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass},
                (proxy,method,args)->{
                    if (consumer == null) init();
                    consumer.setRequest(protocolPrefix+args[0]);
                    return pool.submit(consumer).get();
                });
    }
}
