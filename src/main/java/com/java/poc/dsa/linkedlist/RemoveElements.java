package com.java.poc.dsa.linkedlist;

public class RemoveElements {

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1);
        ListNode list2 = new ListNode(2);
        ListNode list3 = new ListNode(3);
        ListNode list4 = new ListNode(4);
        list1.next = list2;
        list2.next = list3;
        list3.next = list4;

        System.out.println(list1);
        System.out.println("Deleting 3 ...");
        System.out.println(remove(list1,3).toString());
    }

    public static ListNode remove(ListNode head, int val){
        if(head == null){
            return null;
        }
        ListNode pointer = head;
        while(pointer.next != null){
            if(pointer.next.val == val){
                pointer.next = pointer.next.next;
            }else{
                pointer = pointer.next;
            }
        }

        return head.val == val? head.next : head;
    }
}
