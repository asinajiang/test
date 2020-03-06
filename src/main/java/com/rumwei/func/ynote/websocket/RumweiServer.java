package com.rumwei.func.ynote.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
//mark 20200305002
public class RumweiServer {
    public static void main(String[] args) throws Exception{
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) //为bossGroup提供日志处理Handler，Netty自带的
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            //因为基于Http协议，因此加入Http的编解码器
                            sc.pipeline().addLast(new HttpServerCodec());
                            //服务器与客户端是以块的方式写，添加ChunkedWrite处理器
                            sc.pipeline().addLast(new ChunkedWriteHandler());
                            //Http协议报文在传输过程中是分段的，Netty提供了一个处理器来聚合这些分段数据，然后再一起处理
                            sc.pipeline().addLast(new HttpObjectAggregator(8192));
                            //对于websocket，数据是以帧的形式来传递的，该Handler可以将Http协议升级为websocket协议，并保持长连接
                            //具体交互是浏览器发送http请求到服务器，当到达此Handler时，判断uri中是否含有/hello,如果包含，则返回浏览器告知浏览器
                            //将http升级为websocket协议再来请求,因此浏览器实际发送了两次请求。这也是为何需要浏览器也支持websocket才能实现的原因。
                            sc.pipeline().addLast(new WebSocketServerProtocolHandler("/hello")); //过滤"/hello"的uri
                            //自定义handler，处理业务逻辑
                            sc.pipeline().addLast(new MyTextWebSocketFrameHandler());
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
