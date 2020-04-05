package com.rumwei.func.interview;

import java.util.Scanner;

/**
 * interview-20200405-001
 * 最长回文子串
 * 测试例：abcdcefghijihgjklm
 * */
public class ZuiChangHuiWenZiChuan {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            String input = scan.nextLine();
            String res = longestPalindrome2(input);
            System.out.println(res);
        }
        scan.close();

    }

    /**
     * 对输入的字符串进行预处理，解决奇偶问题，即处理aba与aa这两种类型回文的区别
     * 假设在保证原字符串没有^,$,#这三个符号的前提下，aba将会被预处理成^#a#b#a#$
     * */
    public static String preProcess(String s) {
        int n = s.length();
        if (n == 0) {
            return "^$";
        }
        String ret = "^";
        for (int i = 0; i < n; i++)
            ret += "#" + s.charAt(i);
        ret += "#$";
        return ret;
    }

    // 马拉车算法
    public static String longestPalindrome2(String s) {
        String T = preProcess(s);
        int n = T.length();
        int[] P = new int[n]; //P[index]表示以T.charAt(index)为中心，两边相同字符的个数，如c#a#b#a#d,b的index为10，则P[10]=3
        /**
         * 计算P[i]
         * 基于前面某个index=C的回文信息来计算，且P[C]+C=R；在计算P[i]时，如何确定C：在<=i的所有index中，P[C]+C最大，即C能使R最大
         * */
        int C = 0, R = 0; //C,R初始值
        for (int i = 1; i < n - 1; i++) {  //不遍历^和$
            //开始计算P[i]
            int i_mirror = 2 * C - i; //获取i相对于C的对称点i_mirror，P[i_mirror]是已知值
            if (R > i) { //即i_mirror~i是包含在C-P[C]~C+P[C](=R)区间范围内的
                //此处为何取两者的最小值：因为目前只能保证C-P[C]~i_mirror与i~C+P[C](=R)区间的状况是一样的，超过R的部分目前是未知的，
                //因此首先需要保证P[i]<=R-i。
                //而在C-P[C]<i_mirror-P[i_mirror]情况下，P[i]=P[i_mirror],
                //综上，才有取两者的最小值
                //实际的逻辑：
                //P[i] = P[i_mirror]
                //if(P[i] > R-i) P[i] = R-i;
                P[i] = Math.min(R - i, P[i_mirror]);
            } else {
                //前面已知的信息只截止到R，因此此时需要重新计算P[i],所以P[i]的起点从0开始
                P[i] = 0;
            }
            //以上是利用已知的情况，拿到了P[i]的最小值，即P[i]的起点，之后就是遍历未知的区域计算P[i]的真实值
            //利用中心扩展法，i~i+P[i]是不用再计算了的，因此从i+1+P[i]开始算
            while (T.charAt(i + 1 + P[i]) == T.charAt(i - 1 - P[i])) { //因^和$的约束，不会越界
                P[i]++;
            }

            //以上P[i]的计算已经完成，现在看看已知区域(即R的最大值)是否需要更新
            //实际就是判断当前的i是否需要替代原有的C和R
            if (i + P[i] > R) {
                C = i;
                R = i + P[i];
            }

        }

        // 找出 P 的最大值，即取出最长回文子串
        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (P[i] > maxLen) {
                maxLen = P[i];
                centerIndex = i;
            }
        }
        int start = (centerIndex - maxLen) / 2; //最开始讲的求原字符串下标
        return s.substring(start, start + maxLen);
    }
}
