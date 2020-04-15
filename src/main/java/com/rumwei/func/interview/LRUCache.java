package com.rumwei.func.interview;
/**
 * interview-20200405-002
 * RLU缓存机制实现：获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
 * 写入数据 put(key,value)-如果密钥不存在,则写入其数据值.当缓存容量达到上限时,它应该在写入新数据之前删除最久未使用的数据值,保持容量不超.
 * 要求：get与put都是O(1)的复杂度，put的key如果已存在，则覆盖
 * */

import java.util.HashMap;
import java.util.Map;

/**
 * 分析：如果仅仅使用Map来存(key,value)值 以及 LinkedList来存keys的新鲜度，则get操作时，更新keys的新鲜度无法做到O(1)复杂度
 * 为了达到这一目的，需要借用双向链表，即利用双向链表中的元素自身携带其在链表中的位置的特性，实现更新keys的O(1)的目的
 * */
public class LRUCache {
    class Node{
        int key;
        int value;
        Node prev;
        Node next;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

    //为防止空异常，同时便于操作头尾，设置两个头尾虚拟节点
    private Node head;
    private Node tail;
    private int capacity;
    public LRUCache(int capacity){
        this.head = new Node(0,0);
        this.tail = new Node(0,0);
        head.next = tail;
        tail.prev = head;
        this.capacity = capacity;
    }
    //存储缓存数据
    Map<Integer,Node> map = new HashMap<>();

    public int get(int key){
        if(map.get(key) != null){
            //更新访问新鲜度
            Node node = map.get(key);
            //先移除node
            Node preNode = node.prev;
            Node nextNode = node.next;
            preNode.next = nextNode;
            nextNode.prev = preNode;

            //然后将node转到头部
            Node oriHead = head.next;
            head.next = node;
            node.prev = head;
            node.next = oriHead;
            oriHead.prev = node;

            return node.value;
        }else {
            return -1;
        }
    }

    public void put(int key, int value){
        if(map.get(key) != null){
            //直接覆盖以及更新新鲜度
            Node node = map.get(key);
            //先移除node
            Node preNode = node.prev;
            Node nextNode = node.next;
            preNode.next = nextNode;
            nextNode.prev = preNode;

            //然后将node转到头部
            Node oriHead = head.next;
            head.next = node;
            node.prev = head;
            node.next = oriHead;
            oriHead.prev = node;

            node.value = value;
            map.put(key,node);
        }else {
            if(map.size() >= capacity){
                //节点链移除最后一个节点
                Node nodeToDel = tail.prev;
                Node preNodeToDeal = nodeToDel.prev;
                preNodeToDeal.next = tail;
                tail.prev = preNodeToDeal;

                //map移除最后一个节点
                map.remove(nodeToDel.key);
            }
            //新增节点
            Node node = new Node(key,value);
            map.put(key,node);
            //塞入节点链
            Node headNext = head.next;
            head.next = node;
            node.prev = head;
            node.next = headNext;
            headNext.prev = node;
        }
    }

    //test area begin
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(4);
        System.out.println(cache.get(2));
        cache.put(2,20);
        System.out.println(cache.get(2));
        cache.put(1,10);
        cache.put(3,30);
        cache.put(4,40);
        cache.put(5,50); //超过容量，2会被移除
        System.out.println(cache.get(2));
        System.out.println(cache.get(1)); //刷新1的新鲜度
        cache.put(6,60); //超过容量，1因刷新过，所以3会被移除
        System.out.println(cache.get(3));
    }
    //test area end

}
