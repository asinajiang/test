package com.rumwei.func.interview;

/**
 * interview-20200402-001
 * 简单背包问题
 * */
public class SimplePackage {
    static int sum = 100; //总空间
    static int num = 4; //总物件数
    static int[] room = {50,20,30,15}; //各物件占用的空间
    static int[] value = {30,70,80,100}; //各物件的价值

    public static void main(String[] args) {
        //数组含义：横坐标-物品数量，纵坐标-空间大小，值-总价值
        int[][] res = new int[num+1][sum+1]; //包括边界0，因此要+1
        for (int i=0; i<=num; i++){
            for (int j=0; j<=sum; j++){
                if (i == 0 || j == 0){
                    res[i][j] = 0;
                }else {
                    if (room[i-1] > j) res[i][j] = res[i-1][j]; //第i个物品超过总容量限制
                    //res[i-1][j]--不放第i个物品，
                    //res[i-1][j-room[i-1]]+value[i-1]--放第i个物品，前i-1个物品就只能占用j-room[i-1]的空间，但是总价值要+value[i-1]
                    else res[i][j] = max(res[i-1][j],res[i-1][j-room[i-1]]+value[i-1]);
                }
            }
        }
        System.out.println(res[num][sum]);
    }

    private static int max(int i, int j){
        return (i>j)?i:j;
    }


}
