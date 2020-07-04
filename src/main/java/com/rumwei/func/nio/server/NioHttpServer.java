package com.rumwei.func.nio.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NioHttpServer implements Runnable{

    private ServerSocketChannel serverSocketChannel; //只负责搬运buffer，不与具体数据打交道
    private Selector selector; //管理多个连接
    private ByteBuffer readBuffer = ByteBuffer.allocate(8912); //作为数据的载体
    private List<ChangeRequest> changeRequests = new LinkedList<>();
    private Map<SocketChannel, List<ByteBuffer>> pendingSent = new HashMap<>();
    private List<RequestHandler> requestHandlers = new ArrayList<>();

    public NioHttpServer(InetAddress address, int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(address, port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        SelectionKey key = null;
        while (true) {
            try{
                synchronized (changeRequests) {
                    for (ChangeRequest request : changeRequests) {
                        switch (request.type) {
                            case ChangeRequest.CHANGEOPS:
                                key = request.socketChannel.keyFor(selector);
                                if (key != null && key.isValid()) {
                                    key.interestOps(request.op);
                                }
                                break;
                        }
                    }
                    changeRequests.clear();
                }
                System.out.println("start selector");
                selector.select();
                System.out.println("something happened");
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    key = selectedKeys.next();
                    selectedKeys.remove();
                    if (!key.isValid()) continue;
                    if (key.isAcceptable()) {
                        accept(key);
                    }else if (key.isReadable()) {
                        read(key);
                    }else if (key.isWritable()) {
                        write(key);
                    }
                }
            }catch(Exception e){
                if (key != null) {
                    key.cancel();
                    Util.closeQuietly(key.channel());
                }
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }
    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        readBuffer.clear();
        int numRead;
        try{
            numRead = socketChannel.read(readBuffer);
        }catch(IOException e){
            key.cancel();
            socketChannel.close();
            return;
        }
        if (numRead == -1) {
            socketChannel.close();
            key.cancel();
            return;
        }
        int worker = socketChannel.hashCode() % requestHandlers.size();
        requestHandlers.get(worker).processData(socketChannel, readBuffer.array(), numRead);
    }
    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        synchronized (pendingSent) {
            List<ByteBuffer> queue = pendingSent.get(socketChannel);
            while (!queue.isEmpty()) {
                ByteBuffer buf = queue.get(0);
                socketChannel.write(buf);
                if (buf.remaining() > 0) {
                    break;
                }
                queue.remove(0);
            }
            if (queue.isEmpty()) {
                key.interestOps(SelectionKey.OP_READ);
            }
        }
    }

    public void send(SocketChannel socket, byte[] data) {
        synchronized (changeRequests) {
            changeRequests.add(new ChangeRequest(socket, ChangeRequest.CHANGEOPS, SelectionKey.OP_WRITE));
            synchronized (pendingSent) {
                List<ByteBuffer> queue = pendingSent.get(socket);
                if (queue == null) {
                    queue = new ArrayList<>();
                    pendingSent.put(socket, queue);
                }
                queue.add(ByteBuffer.wrap(data));
            }
        }
        selector.wakeup();
    }

    //服务器启动
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8746;
        NioHttpServer server = new NioHttpServer(null, port);
        //start requestHandler
        for (int i=0; i<4; i++) {
            RequestHandler handler = new RequestHandler(server, "/conan/rumwei");
            server.requestHandlers.add(handler);
            new Thread(handler, "Request-Handler-Thread-" + i).start();
        }
        TimeUnit.SECONDS.sleep(2);
        //start server
        new Thread(server, "Server-Thread").start();
        System.out.println("server started...");
    }
}
