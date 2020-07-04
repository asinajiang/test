package com.rumwei.func.nio.server;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class RequestHeaderDecoder {

    private static CharsetDecoder decoder = Charset.forName("ISO-8859-1").newDecoder();
    private static final byte[] END  = new byte[] {13,10,13,10}; //13--回车 10--换行
    private static final byte[] GET  = new byte[] {71,69,84,32}; //71--'G' 69--'E' 84--'T' 32--' '空格
    private static final byte[] HEAD = new byte[] {72,69,65,68}; //72--'H' 69--'E' 65--'A' 68--'D'

    private boolean begin = false;
    private CharBuffer charBuffer = ByteBuffer.allocate(2048).asCharBuffer();
    private Map<String, String> headerMap = new TreeMap<>();
    private String resource;
    private VERB_ENUM verb;

    public boolean appendSegment(byte[] segment) {
        int beginIndex = 0;
        if (!begin) {
            if ((beginIndex = Util.findFirstIndex(segment, GET, 0)) != segment.length) {
                //表示segment包含GET子串
                begin = true;
                headerMap.clear();
                verb = VERB_ENUM.GET;
            }else if ((beginIndex = Util.findFirstIndex(segment, HEAD, 0)) != segment.length) {
                //表示segment包含HEAD子串
                begin = true;
                headerMap.clear();
                verb = VERB_ENUM.HEAD;
            }else {
                //not begin yet and find no begin, just return false
                return false;
            }
        }
        int endIndex = Util.findLastIndex(segment, END);
        ByteBuffer b = ByteBuffer.wrap(segment, beginIndex, endIndex-beginIndex); //todo 有修改
        decoder.decode(b, charBuffer, endIndex != segment.length);
        if (endIndex != segment.length) {
            charBuffer.flip();
            String head = charBuffer.toString();
            String[] lines = head.split("\r\n");
            String[] split = lines[0].split(" ");
            resource = split[1];
            for (int i=1; i<lines.length; i++) {
                String[] lineData = lines[i].split(":");
                headerMap.put(lineData[0].trim(), lineData[1].trim());
            }
            charBuffer.clear();
            decoder.reset();
            begin = false;
            return true;
        }
        return false;
    }

    public String getHeader(String key) {
        return headerMap.get(key);
    }
    public Set<String> getHeaderNames() {
        return headerMap.keySet();
    }
    public String getResource() {
        return resource;
    }
    public VERB_ENUM getVerb() {
        return verb;
    }

    enum VERB_ENUM {
        CONNECT, DELETE, GET, HEAD, OPTIONS, PATCH, POST, PUT, TRACE
    }
}
