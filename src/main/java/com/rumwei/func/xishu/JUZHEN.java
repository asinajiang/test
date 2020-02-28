package com.rumwei.func.xishu;

public class JUZHEN {
    int m;
    int n;
    int size;
    XISHU[] array;
    public JUZHEN(int m, int n){
        this.m = m;
        this.n = n;
        this.size = 9*getBits(m)+9*getBits(n);
        array = new XISHU[size];
    }

    private static int getBits(int num){
        int res = 1;
        while (num/10 > 0){
            res++;
            num = num/10;
        }
        return res;
    }

    private static int index(int i, int j){
        int res = 0;
        while (i/10>0){
            res += i%10;
            i = i/10;
        }
        res += i;
        while (j/10>0){
            res += j%10;
            j = j/10;
        }
        res += j;
        return res;
    }



    public void set(int i, int j, float value){
        if(array[index(i,j)] == null){
            array[index(i,j)] = new XISHU(i,j,value);
        }else{
            array[index(i,j)].next = new XISHU(i,j,value);
        }
    }

    public float get(int i, int j){
        int index = index(i,j);
        if(array[index] == null){
            return 0;
        }else{
            return array[index].getRight(i,j).value;
        }
    }



}
