package com.rumwei.func.nio.server;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RequestHandler implements Runnable {

    private static final DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    static {
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private File currentFile;
    private Date lastModified;
    /**
     * class RequestSegmentHeader {
     *     SocketChannel client;
     *     byte[] data;
     * }
     * */
    private List<RequestSegmentHeader> pendingRequestSegment = new ArrayList<>();
    private Map<SocketChannel, RequestHeaderDecoder> socketChannelDecoderMap = new WeakHashMap<>();
    private NioHttpServer server;
    private String serverRoot;
    private String acceptEncoding;

    public RequestHandler(NioHttpServer server, String serverRoot) {
        this.server = server;
        this.serverRoot = serverRoot;
    }

    public void processData(SocketChannel client, byte[] data, int size) {
        byte[] dataCopy = new byte[size];
        System.arraycopy(data, 0, dataCopy, 0, size);
        synchronized (pendingRequestSegment) {
            pendingRequestSegment.add(new RequestSegmentHeader(client, dataCopy));
            pendingRequestSegment.notify();
        }
    }

    @Override
    public void run() {
        RequestSegmentHeader requestSegmentHeader = null;
        RequestHeaderDecoder headerDecoder = null;
        ResponseHeaderBuilder builder = new ResponseHeaderBuilder();
        byte[] head = null;
        byte[] body = null;
        String file = null;
        String mime = null;
        boolean zip = false;

        //wait for data
        while (true) {
            synchronized (pendingRequestSegment) {
                while (pendingRequestSegment.isEmpty()) {
                    try{
                        System.out.println("requestHandler is waiting");
                        pendingRequestSegment.wait();
                    }catch(InterruptedException e){
                        System.out.println("error occurs while requestHandler is waiting");
                    }
                }
                System.out.println("requestHandler start handling...");
                requestSegmentHeader = pendingRequestSegment.remove(0);
            }
            headerDecoder = socketChannelDecoderMap.get(requestSegmentHeader.client);
            if (headerDecoder == null) {
                headerDecoder = new RequestHeaderDecoder(); //Decoder是无状态的
                socketChannelDecoderMap.put(requestSegmentHeader.client, headerDecoder);
            }
            try {
                if (headerDecoder.appendSegment(requestSegmentHeader.data)) {
                    file = serverRoot + headerDecoder.getResource();
                    currentFile = new File(file);
                    mime = Util.getContentType(currentFile);
                    acceptEncoding = headerDecoder.getHeader(ResponseHeaderBuilder.ACCEPT_ENCODING);
                    zip = mime.contains("text") && acceptEncoding != null && acceptEncoding.contains("gzip");
                    builder.clear();
                    builder.addHeader(ResponseHeaderBuilder.CONNECTION, ResponseHeaderBuilder.KEEP_ALIVE)
                            .addHeader(ResponseHeaderBuilder.CONTENT_TYPE, mime);
                    body = Util.file2ByteArray(currentFile, zip);
                    builder.addHeader(ResponseHeaderBuilder.CONTENT_LENGTH, body.length);
                    if (zip) {
                        builder.addHeader(ResponseHeaderBuilder.CONTENT_ENCODING, ResponseHeaderBuilder.GZIP);
                    }
                    lastModified = new Date(currentFile.lastModified());
                    builder.addHeader(ResponseHeaderBuilder.LAST_MODIFIED, format.format(lastModified));
                    head = builder.getHeader();
                    server.send(requestSegmentHeader.client, head);
                    if (body != null && headerDecoder.getVerb() == RequestHeaderDecoder.VERB_ENUM.GET) {
                        server.send(requestSegmentHeader.client, body);
                    }
                }
            }catch (IOException e) {
                builder.addHeader(ResponseHeaderBuilder.CONTENT_LENGTH, 0).setStatus(ResponseHeaderBuilder.NOT_FOUND_404);
                head = builder.getHeader();
                server.send(requestSegmentHeader.client, head);
            }catch (Exception e) {
                builder.addHeader(ResponseHeaderBuilder.CONTENT_LENGTH, 0).setStatus(ResponseHeaderBuilder.SERVER_ERROR_500);
                head = builder.getHeader();
                server.send(requestSegmentHeader.client, head);
            }
        }
    }
}
