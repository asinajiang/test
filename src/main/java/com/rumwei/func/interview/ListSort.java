package com.rumwei.func.interview;
/**
 * interview-20200405-003
 * 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。
 * */

import com.rumwei.func.interview.common.ListNode;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class ListSort {
    public static ListNode sortList(ListNode head) {
        if(head == null || head.next == null) return head;
        //等分拆分链表
        //0-head-ele1-ele2-ele3-ele4-ele5        6个元素
        //0-head-ele1-ele2-ele3-ele4-ele5-ele6   7个元素
        ListNode T = new ListNode(0);
        T.next = head;
        ListNode toMid = T;
        ListNode toTail = T;
        while (toTail.next != null && toTail.next.next != null){
            toMid = toMid.next;
            toTail = toTail.next.next;
        }
        ListNode right = toMid.next; //执行等分
        toMid.next = null; //执行等分
        ListNode leftHalf = sortList(head);
        ListNode rightHalf = sortList(right);
        ListNode result = merge(leftHalf,rightHalf);
        return result;
    }
    //对排好序的l1和l2进行合并
    public static ListNode merge(ListNode l1, ListNode l2){
        ListNode result = new ListNode(0);
        ListNode scan = result;
        while(l1 != null || l2 != null){
            if(l1 == null){
                scan.next = l2;
                scan = scan.next;
                l2 = l2.next;
            }else if(l2 == null){
                scan.next = l1;
                scan = scan.next;
                l1 = l1.next;
            }else{
                if(l1.val < l2.val){
                    scan.next = l1;
                    scan = scan.next;
                    l1 = l1.next;
                }else {
                    scan.next = l2;
                    scan = scan.next;
                    l2 = l2.next;
                }
            }
        }
        return result.next;
    }

    public static void main(String[] args) {
        int[] array = {-1,5,3,4,0};
        ListNode T = new ListNode(0);
        ListNode scan = T;
        for(int i=0; i<array.length; i++){
            ListNode node = new ListNode(array[i]);
            scan.next = node;
            scan = scan.next;
        }
        ListNode sorted = sortList(T.next);
        while(sorted != null){
            System.out.println(sorted.val);
            sorted = sorted.next;
        }

    }
}
