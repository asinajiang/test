package com.rumwei.func.nio.server;

public class FileItem implements Comparable<FileItem>{
    public final String href;
    public final String name;
    public final String size;
    public final String mtime;

    public FileItem(String href, String name, String size, String mtime) {
        this.href = href;
        this.name = name;
        this.size = size;
        this.mtime = mtime;
    }
    public int compareTo(FileItem o) {
        return name.compareTo(o.name);
    }
}

