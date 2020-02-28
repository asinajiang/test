package com.rumwei.func.regex;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public char value;
    public Node node; //当前结点的下一个结点
    public List<Node> linkNodes; //当前结点的下一个状态结点的集合
    public boolean isHead; //标记为状态机头
    public boolean isTail; //标记为状态机尾
    public Node(char value){
        this.value = value;
        this.linkNodes = new ArrayList<>();
    }
}
