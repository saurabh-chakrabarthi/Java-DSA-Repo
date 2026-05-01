package com.java.poc.dsa.linkedlist;

public class Merge2SortedLinkedLists {
    public static void main(String[] args) {
        ListNode listA1 = new ListNode(1);
        ListNode listA2 = new ListNode(2);
        ListNode listA3 = new ListNode(3);
        listA1.next = listA2;
        listA2.next = listA3;
        System.out.println(listA1);

        ListNode listB1 = new ListNode(1);
        ListNode listB2 = new ListNode(4);
        ListNode listB3 = new ListNode(6);
        listB1.next = listB2;
        listB2.next = listB3;
        System.out.println(listB1);

        ListNode finalList = mergeLists(listA1,listB1);
        System.out.println(finalList.toString());
    }

    private static ListNode mergeLists(ListNode listA, ListNode listB) {
        ListNode head = new ListNode(0);
        ListNode p=head;
        while(listA!=null && listB!=null){
            if(listA.val < listB.val){
                p.next = listA;
                listA = listA.next;
            }else{
                p.next = listB;
                listB = listB.next;
            }
            p=p.next;
        }

        if(listA!=null){
            p.next = listA;
        }

        if(listB!=null){
            p.next = listB;
        }

        return head.next;
    }
}
