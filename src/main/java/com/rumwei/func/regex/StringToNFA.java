package com.rumwei.func.regex;

import java.util.ArrayList;
import java.util.List;

//NFC 非确定状态自动机
public class StringToNFA {
    //将匹配规则转换为非确定状态自动机
    public static List<Node> strToNodes(String matcher){
        List<Node> nodes = new ArrayList<>();
        for (int i=0; i<matcher.length(); i++){
            nodes.add(new Node(matcher.charAt(i)));
        }
        //遍历nodes
        for (int j=0; j<nodes.size(); j++){
            Node currentNode = nodes.get(j);
            //本结点value为普通字符时，上个结点指向本结点
            //本结点value为 . 时，上个结点指向本结点
            if ((currentNode.value >= 'a' && currentNode.value <= 'z') || String.valueOf(currentNode.value).equals(".")){
                if (j > 0){
                    nodes.get(j-1).node = currentNode;
                }
            }
            //本结点value为 * 时，上个结点增加一个指向自己的状态，同时增加一个指向下一个结点的状态并删除本结点
            if (String.valueOf(currentNode.value).equals("*")){
                if (j > 0){
                    nodes.get(j-1).linkNodes.add(nodes.get(j-1));
                }
                if (j > 0 && j < nodes.size()-1){
                    nodes.get(j-1).linkNodes.add(nodes.get(j+1));
                }
                nodes.remove(currentNode);
                j--;
            }
        }
        nodes.get(0).isHead = true;
        nodes.get(nodes.size()-1).isTail = true;
        return nodes;
    }
}
