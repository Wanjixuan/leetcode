## 分治

### 思路
可以把问题简化为：不停的合并两个有序链表

不停的把数组细分，接着把最细分的两个链表合并


### 代码

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        
        return mergeList(lists, 0, lists.length - 1);
    }

    public ListNode mergeList(ListNode[] lists, int left, int right) {
        // 从中间分开，寻找两个链表进行拼接
        // 先尽量分才对，这样分治才有用
        // 分的条件：左右都相等了
        if (left == right) return lists[left];
        int mid = left + (right - left) / 2;
        ListNode l1 = mergeList(lists, left, mid);
        ListNode l2 = mergeList(lists, mid + 1, right);

        return merge(l1, l2);
        
    }

    public ListNode merge(ListNode l1, ListNode l2) {
        // 将两个链表递归进行拼接
        // 函数作用：比较两个传入链表头结点的值，把大的接在小的后面
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        if (l1.val > l2.val) {
            // 这时候把 l1 接在 l2.next的后面，才能形成升序
            l2.next = merge(l1, l2.next);
            return l2;
        } else {
            // 把 l2 接在 l1.next的后面
            l1.next = merge(l1.next, l2);
            return l1;
        }
    }
}
```


## 优先队列
### 思路
与[https://leetcode-cn.com/problems/design-twitter/](355. 设计推特)中，取出按时间顺序前K的元素相同
数组中每个链表都已经是升序了，所以先把各链表的**头结点**存进优先队列（最小堆），堆顶就是当前也是整体最小的结点
新建一个哨兵节点，方便操作
每弹出一个结点，都要观察是否有 `Node.next`，有的话再放进优先队列中进行排序。直到队列为空!

### 代码

```java
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        
        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, (a, b)->(a.val - b.val));
        ListNode ret = new ListNode(-1);
        ListNode cur = ret;
        for (ListNode head : lists) {
            if (head == null) continue;
            pq.offer(head);
        }

        while (!pq.isEmpty()) {
            ListNode tmp = pq.poll();
            cur.next = tmp;
            cur = cur.next;
            if (tmp.next != null) {
                pq.offer(tmp.next);
            } 
        }

        return ret.next;
        

    }
}
```
