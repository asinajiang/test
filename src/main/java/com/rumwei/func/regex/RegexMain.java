package com.rumwei.func.regex;

import scala.Int;

import java.util.*;
import java.util.regex.Pattern;

public class RegexMain {
    public static void main(String[] args) {

        int[] sample = {0,1,0,2,1,0,1,3,2,1,2,1};
        int res = trap(sample);
        System.out.println(res);


    }

    public static int trap(int[] height) {
        int result = 0;
        int[] topIndex = topIndexGen(height,0,height.length-1);
        // if(topIndex.length = 1){
        //     if(topIndex[0] == 0){
        //         return rightSize(height,0,height.length-1);
        //     }
        //     if(topIndex[0] == height.length-1){
        //         return leftSize(height,0,height.length-1);
        //     }
        //     return leftSize(height,0,topIndex[0]) + rightSize(height,topIndex[0],right);
        // }
        if(topIndex[0] > 0){
            result += leftSize(height,0,topIndex[0]);
        }
        result += middleSize(height,topIndex);
        if(topIndex[topIndex.length-1] < height.length-1){
            result += rightSize(height,topIndex[topIndex.length-1],topIndex.length-1);
        }
        return result;
    }

    private static int middleSize(int[] height, int[] topIndex){
        int result = 0;
        if(topIndex.length == 1){
            return result;
        }
        for(int i=0; i<topIndex.length-1; i++){
            int band = topIndex[i+1]-topIndex[i];
            if(band > 1){
                result += (band-1)*height[topIndex[i]];
                for(int j=topIndex[i]+1; j<topIndex[i+1]; j++){
                    result -= height[j];
                }
            }
        }
        return result;
    }

    //找i到j区间内最高的点对应的index
    private static int[] topIndexGen(int[] height, int left, int right){
        List<Integer> list = new ArrayList<>();
        int max = height[left];
        for(int i=left; i<=right; i++){
            if(max < height[i]){
                max = height[i];
            }
        }
        int index = 0;
        for(int i=left; i<=right; i++){
            if(max == height[i]){
                list.add(i);
            }
        }
        int[] result = new int[list.size()];
        for(Integer ele : list){
            result[index++] = ele.intValue();
        }
        return result;
    }

    private static int leftSize(int[] height, int left, int right){
        if (right - left <= 1){
            return 0;
        }
        int[] heightCopy = arrayCopy(height);
        int result = 0;
        int[] topIndex = topIndexGen(heightCopy,left,right-1);
        topIndex = arrayAppend(topIndex,right);
        heightCopy[right] = heightCopy[topIndex[0]];
        boolean b2 = (topIndex[0] == left);
        if(b2){
            result = middleSize(heightCopy,topIndex);
        }else{
            result = middleSize(heightCopy,topIndex) + leftSize(heightCopy,left,topIndex[0]);
        }
        return result;
    }

    private static int rightSize(int[] height, int left, int right){
        if (right - left <= 1){
            return 0;
        }
        int[] heightCopy = arrayCopy(height);
        int result = 0;
        int[] topIndex = topIndexGen(heightCopy,left+1,right);
        topIndex = headInsert(topIndex,left);
        heightCopy[left] = heightCopy[topIndex[topIndex.length-1]];
        boolean b2 = (topIndex[topIndex.length-1] == right);
        if(b2){
            result = middleSize(heightCopy,topIndex);
        }else{
            result = middleSize(heightCopy,topIndex) + rightSize(heightCopy,topIndex[topIndex.length-1],right);
        }
        return result;
    }

    private static int[] arrayCopy(int[] input){
        int[] output = new int[input.length];
        for(int i=0; i<input.length; i++){
            output[i] = input[i];
        }
        return output;
    }

    private static int[] arrayAppend(int[] input, int last){
        int[] output = new int[input.length+1];
        for(int i=0; i<input.length; i++){
            output[i] = input[i];
        }
        output[output.length-1] = last;
        return output;
    }

    private static int[] headInsert(int[] input, int first){
        int[] output = new int[input.length+1];
        output[0] = first;
        for(int i=0; i<input.length; i++){
            output[i+1] = input[i];
        }
        return output;
    }


}
