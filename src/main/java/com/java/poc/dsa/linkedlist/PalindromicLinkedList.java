package com.java.poc.dsa.linkedlist;

public class PalindromicLinkedList {

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1);
        ListNode list2 = new ListNode(2);
        ListNode list3 = new ListNode(3);
        list1.next = list2;
        list2.next = list3;

        System.out.println(list1.toString());
        System.out.println(isPalindrome(list1));
    }

    public static boolean isPalindrome(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        slow = reverse(slow);
        fast = head;

        while(slow != null){
            if(slow.val != fast.val){
                return false;
            }

            slow = slow.next;
            fast = fast.next;
        }

        return true;
    }

    private static ListNode reverse(ListNode head){
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
}
