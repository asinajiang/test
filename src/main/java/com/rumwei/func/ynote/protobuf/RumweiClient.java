package com.rumwei.func.ynote.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
public class RumweiClient {
    public static void main(String[] args) throws Exception{
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            //加入ProtoBufEncoder
                            sc.pipeline().addLast("protoBufEncoder",new ProtobufEncoder());
                            sc.pipeline().addLast("clientHandler",new RumweiClientHandler());
                        }
                    });
            ChannelFuture future = client.connect("localhost", 7000).sync();
            future.channel().closeFuture().sync();
        }finally {
            workGroup.shutdownGracefully();
        }
    }
}
