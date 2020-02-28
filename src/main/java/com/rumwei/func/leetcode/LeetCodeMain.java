package com.rumwei.func.leetcode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LeetCodeMain {

    public static void main(String[] args) {


        Solution solution = new Solution();
        String bw = "hot";
        String ew = "dog";
        List<String> list = new ArrayList<>();
        list.add("hot");
        list.add("dog");
        list.add("cog");
        list.add("pot");
        list.add("dot");
        int len = solution.ladderLength(bw,ew,list);
        System.out.println(len);

    }


}
