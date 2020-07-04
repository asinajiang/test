//package com.rumwei.func.nio.server;
//
//import java.lang.ref.ReferenceQueue;
//import java.lang.ref.SoftReference;
//
//public class ButterflySoftCache {
//
//
//    //region 内部静态类:CacheEntry和MapEntry
//    public static class CacheEntry {
//        public byte[] header;
//        public byte[] body;
//        public CacheEntry(byte[] header, byte[] body) {
//            this.header = header;
//            this.body = body;
//        }
//    }
//    public static class MapEntry extends SoftReference<CacheEntry> {
//        String key;
//        public MapEntry(String key, CacheEntry referent, ReferenceQueue<CacheEntry> queue) {
//            super(referent, queue);
//            this.key = key;
//        }
//    }
//    //endregion
//}
