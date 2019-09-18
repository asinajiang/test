package com.rumwei.func.md5;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class MD5Test {
    public static void main(String[] args)throws Exception{

        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);



        String str = "Who are you and the sky is very blue haha";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(str.getBytes());
        gzip.finish();
        gzip.close();
        String encodeStr = new String(encode(bos.toByteArray()));
        System.out.println(encodeStr);
        bos.close();
        //uncompress
        byte[] temp = decode(encodeStr);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(temp);
        GZIPInputStream unGZIP = new GZIPInputStream(in);
        byte[] buffer = new byte[1024];
        int n;
        while ((n=unGZIP.read(buffer)) >= 0){
            out.write(buffer,0,n);
        }
        byte[] unPressResult = out.toByteArray();
        System.out.println(new String(unPressResult));




    }


    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    private static String encodeToString(byte[] bytes) {
        char[] encodedChars = encode(bytes);
        return new String(encodedChars);
    }

    private static char[] encode(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }
    public static byte[] decode(String hex) {
        return decode(hex.toCharArray());
    }
    public static byte[] decode(char[] data) throws IllegalArgumentException {
        int len = data.length;
        if ((len & 0x01) != 0) {
            throw new IllegalArgumentException("Odd number of characters.");
        }
        byte[] out = new byte[len >> 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return out;
    }
    protected static int toDigit(char ch, int index) throws IllegalArgumentException {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new IllegalArgumentException("Illegal hexadecimal charcter " + ch + " at index " + index);
        }
        return digit;
    }






}
