package com.rumwei.func.leetcode;

import java.util.*;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int res = find(beginWord,endWord,wordList,0);
        return res==0?0:res+1;
    }

    private int find(String bw,String ew,List<String> wordList,int len){
        if(wordList.size() == 0) return 0;
        int result = 0;
        int mark = 0;
        for(String ele : wordList){
            if(isMid(bw,ele)){
                mark = 1;
                if(ew.equals(ele)){
                    if(result == 0){
                        result = len + 1;
                    }else{
                        result = min(result,len+1);
                    }
                }else{
                    List<String> wordCopy = new ArrayList<>();
                    wordCopy.addAll(wordList);
                    wordCopy.remove(ele);
                    int resTemp = find(ele,ew,wordCopy,len+1);
                    if (resTemp != 0){
                        if(result == 0){
                            result = resTemp;
                        }else{
                            result = min(result,resTemp);
                        }
                    }

                }
            }
        }
        if(mark == 0) result = 0;
        // if(len == 0){
        //     return result;
        // }else{
        //     return min(result,len);
        // }
        return result;
    }

    private boolean isMid(String e1, String e2){
        int num = 0;
        for(int i=0; i<e1.length(); i++){
            if(e1.charAt(i) != e2.charAt(i)){
                num++;
                if(num > 1){
                    return false;
                }
            }
        }
        return num == 1;
    }

    private int min(int i1, int i2){
        return i1>i2?i2:i1;
    }
}

