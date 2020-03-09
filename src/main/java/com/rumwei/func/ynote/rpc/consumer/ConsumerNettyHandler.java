package com.rumwei.func.ynote.rpc.consumer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.Callable;
public class ConsumerNettyHandler extends ChannelInboundHandlerAdapter implements Callable<String> {
    private ChannelHandlerContext context;
    private String response; //provider返回的结果
    private String request; //调用provider时传入的参数
    @Override
    public synchronized String call() throws Exception {
        context.writeAndFlush(request);
        wait();
        return response; //在channelRead中进行了赋值
    }
    //与服务器连接创建成功后触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx; //自定义方法中会用到该属性
    }
    //收到服务器返回消息时使用
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = msg.toString();
        //本Handler实现了Callable,此处我们在上面的call()中会去做远程调用,因为需要一定时间,因此会立马让线程执行wait()
        //此处就是告诉线程,已经有返回结果了。这也是此方法需要同步的原因
        notify();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
    public void setRequest(String request){
        this.request = request;
    }
}
