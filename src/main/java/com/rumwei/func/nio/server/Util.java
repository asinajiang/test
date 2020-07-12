package com.rumwei.func.nio.server;

import com.samskivert.mustache.Mustache;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPOutputStream;

public class Util {

    //含义类似下方的findFirstIndex，只是本方法是从后往前找
    public static int findLastIndex(byte[] data, byte[] sample) {
        int index = data.length;
        for (int i=data.length-sample.length; i>0; --i) {
            for (int j=0; j<sample.length; j++) {
                if (data[i] == sample[j]) {
                    ++i; ++j;
                    if (j == sample.length) {
                        index = i - sample.length;
                        return index;
                    }
                }else {
                    i = i-j;
                    break;
                }
            }
        }
        return index;
    }
    //尝试从data中找到第一个与sample一致的子串，若找到，则返回子串在data数组中的最左侧index，若没找到，则返回data.length
    public static int findFirstIndex(byte[] data, byte[] sample, int start) {
        int index = data.length;
        for (int i=start; i<data.length; i++) {
            for (int j=0; j<sample.length;) {
                if (data[i] == sample[j]) {
                    ++i;
                    ++j;
                    if (j == sample.length) { //说明找到第一个data中的sample子串
                        index = i - j;
                        return index;
                    }
                }else {
                    i = i - j; //i进行回退，从下一个index重头开始与sample进行比较
                    break;
                }
            }
        }
        return index;
    }

    public static String getContentType(File file) {
        if (file.isDirectory()) {
            return "text/html";
        }
        InputStream ins = Util.class.getClassLoader().getResourceAsStream("mime.types");
        String exten = ""; //文件名后缀
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        if (index != -1) {
            exten = fileName.substring(index+1).toLowerCase();
        }
        Map<String, String> map = new HashMap<>();
        try{
            BufferedReader bis = new BufferedReader(new InputStreamReader(ins));
            String line = null;
            while ((line = bis.readLine()) != null) {
                String[] tmp = line.split("\\s+");
                map.put(tmp[0], tmp[1]);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        if (map.get(exten) == null) {
            return "application/octet-stream"; //默认格式
        }else {
            return map.get(exten);
        }
    }

    public static byte[] file2ByteArray(File file, boolean zip) throws IOException {
        if (file.isFile()) {
            InputStream is = null;
            GZIPOutputStream gzip = null;
            byte[] buffer = new byte[8912];
            ByteArrayOutputStream baos = new ByteArrayOutputStream(8912);
            try {
                if (zip) {
                    gzip = new GZIPOutputStream(baos);
                }
                is = new BufferedInputStream(new FileInputStream(file));
                int read = 0;
                while ((read = is.read(buffer)) != -1) {
                    if (zip) {
                        gzip.write(buffer, 0, read);
                    } else {
                        baos.write(buffer, 0, read);
                    }
                }
            } catch (IOException e) {
                throw e;
            } finally {
                closeQuietly(is);
                closeQuietly(gzip);
            }
            return baos.toByteArray();
        } else if (file.isDirectory()) {
            return directoryList(file, zip);
        } else {
            return new byte[]{};
        }
    }
    public static void closeQuietly(Closeable is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }
    public static byte[] directoryList(File dir, boolean zip) {
        StringBuilder sb = new StringBuilder(300);

        InputStream ins = Util.class.getClassLoader().getResourceAsStream("index.tpl");
        BufferedReader br = new BufferedReader(new InputStreamReader(ins));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
        }

        String html = Mustache.compiler().compile(sb.toString())
                .execute(listDir(dir));

        if (zip) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream(8912);
                GZIPOutputStream gzip = new GZIPOutputStream(baos);
                gzip.write(html.getBytes());
                closeQuietly(gzip);
                return baos.toByteArray();
            } catch (IOException e) {
            }
        } else {
            return html.getBytes();
        }
        return new byte[] {};
    }
    private static DateFormat df = new SimpleDateFormat("yyyy-HH-dd HH:mm:ss");
    public static Object listDir(final File folder) {
        File[] files = folder.listFiles();
        final List<FileItem> fileItems = new ArrayList<FileItem>();
        for (File file : files) {
            String href = file.isDirectory() ? file.getName() + "/" : file
                    .getName();
            String mtime = df.format(new Date(file.lastModified()));
            fileItems.add(new FileItem(href, file.getName(),
                    file.length() + "", mtime));
        }
        Collections.sort(fileItems);
        return new Object() {
            Object files = fileItems;
            Object dir = folder.getName();
        };
    }




}
