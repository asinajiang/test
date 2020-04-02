package com.rumwei.func.interview;
/**
 * interview-20200402-002
 * 合唱团序列问题
 * */
public class SingGroup {
    public static void main(String[] args) {
        int[] height = {186,186,150,200,160,130,197,200}; //N位同学的身高数据
        int[] orderLTR = calculate(height,true);
        int[] orderRTL = calculate(height,false);
        int longest = 0; //最长合唱团序列
        for (int i=0; i<height.length; i++){
            if (longest < orderLTR[i]+orderRTL[i]-1){
                longest = orderLTR[i]+orderRTL[i]-1;
            }
        }
        int num = height.length-longest; //需要出列的最小人数
        System.out.println(num);
    }

    /**
     * @Param height:身高数据
     * @Param order:true-从左到右的最长子序列，false-从右到左的最长子序列
     * @Return int[] len:len[i]表示height[0]~height[i]的最长子序列长度
     * */
    private static int[] calculate(int[] height, boolean order){
        int[] len = new int[height.length];
        if (order) { //从左到右
            for (int i = 0; i < height.length; i++) {
                len[i] = 1; //初始化len[i]为1
                for (int j = 0; j < i; j++) { //遍历len[0]~len[i-1]
                    if (height[i] > height[j] && len[j] + 1 > len[i]) {
                        len[i] = len[j] + 1; //更新len[i]的值
                    }
                }
            }
        }else { //从右到左
            for (int i = height.length-1; i >=0; i--) {
                len[i] = 1; //初始化len[i]为1
                for (int j = height.length-1; j >= i; j--) { //遍历len[0]~len[i-1]
                    if (height[i] > height[j] && len[j] + 1 > len[i]) {
                        len[i] = len[j] + 1; //更新len[i]的值
                    }
                }
            }
        }
        return len;
    }
}
