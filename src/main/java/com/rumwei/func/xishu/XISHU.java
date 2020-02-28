package com.rumwei.func.xishu;

public class XISHU {
    public int i;
    public int j;
    public float value;
    public XISHU next;
    public XISHU getRight(int i, int j){
        XISHU ele = this;
        while (ele != null){
            if (ele.i == i && ele.j == j){
                return ele;
            }else {
                ele = ele.next;
            }
        }
        return null;
    }

    public XISHU(int i, int j, float value) {
        this.i = i;
        this.j = j;
        this.value = value;
    }
}
