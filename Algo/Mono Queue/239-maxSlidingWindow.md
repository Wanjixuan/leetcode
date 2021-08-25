# [239. 滑动窗口最大值](https://leetcode-cn.com/problems/sliding-window-maximum/)

## 思路
首先存储这个窗口的数据结构必须支持：
- 尾插入
- 头删除
- 找到最大值（尽可能减小复杂度的情况下）

前两个特性，利用双链表可以实现！关键在于找到最大值！

![示意图](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/293.png)

普通队列一定满足如下功能<br/>

```java

class Queue{
    // 入队
    void push(E n);
    // 出队
    void pop();
}
```

我需求的不止是这样！
```java
class MonoQueue{
    // 入队
    void push(E n);
    // 出队
    void pop();
    // 上面两个最初始的目的是为了维护窗口的大小
    // 找到最大值！
    E getMax();
  
}
```
如果考虑我把值加进来的时候，就能让我找到最大值，而且最好是在两头，这样最好！<br/>
> 修改push

一个点！我新加进来的数，在当前窗口内一定是最晚被排出去的！<br/>
那么，**我前面比我小的那些数，是不是就不用管！** 我在的时候你不是最大的，我准备走的时候，你早就被pop出这个滑动窗口了<br/>
所以我一被push进来，就需要把前面比我小的，都给弄出去！这样才能最好的压缩空间，然后在存储的数据结构里找到最大值！<br/>

```java
LinkedList<Integer> q = new LinkedList<>();
    public void push(int num) {
        //比我小的给我出去！
        // 并且这样会导致！！队头的元素，永远是最大的！
        while (!q.isEmpty() && q.getLast() < num) {
            q.pollLast();
        } 
        q.addLast(num);
}
```

**值得注意的是，如果我把前面比我小的都给弄出去了，肯定前面要么是一个比我大的，要么就是我自己，这是不是就是一个单调递减的队列？？**

最大值就在队首！！
因此可以得到如何获得最大值的方法了！

```java
    public int getMax() {
        // push的操作，使得队头元素是最大的
        return q.getFirst();
    }
```

再谈`pop`，毫无疑问，被pop的一定是原数组内窗口的第一个。但是！如果他早就被我排挤出去了呢？那是不是就可以直接pass，毕竟题目没有要求我们能够得到`pop`出来的值!
```java
// pop的话，这个数如果被新数push进来而被弄出去了！那么就不用管了
    public void pop(int n) {
        if (n == q.getFirst()) {
            q.pollFirst();
        }
    }
```

## 代码

```java
class Solution {
    // 一个点！我新加进来的数，在当前窗口内一定是最晚被排出去的
    // 那么，我前面比我小的那些数，是不是就不用管！我在的时候你不是最大的，我准备走的时候，你早就被pop出这个滑动窗口了
    // 所以我一被push进来，就需要把前面比我小的，都给弄出去！这样才能最好的压缩空间，然后在存储的数据结构里找到最大值！
        
    // 首先支持头尾插入、删除，双向链表 LinkedList！！
    class MonoQueue{
        LinkedList<Integer> q = new LinkedList<>();
        public MonoQueue() {}
        public void push(int num) {
            //比我小的给我出去！
            // 并且这样会导致！！队头的元素，永远是最大的！
            while (!q.isEmpty() && q.getLast() < num) {
                q.pollLast();
            } 
            q.addLast(num);
        }

        public int getMax() {
            // push的操作，使得队头元素是最大的
            return q.getFirst();
        }

        // pop的话，这个数如果被新数push进来而被弄出去了！那么就不用管了
        public void pop(int n) {
            if (n == q.getFirst()) {
                q.pollFirst();
            }
        }
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        MonoQueue window = new MonoQueue();
        int n = nums.length;
        int[] res = new int[n - k + 1];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (i <= k - 1) {
                // 先把这些放进窗口
                window.push(nums[i]);
                continue;
            } 

            // 放完了
            // 先取出现阶段的最大值
            res[j] = window.getMax();
            // 再往里面放值！
            window.push(nums[i]);
            //维持窗口的长度，弹出窗口的第一个数
            window.pop(nums[i - k]);

            j++;
        }
        // 因为操作顺序的原因，我把最后一个数压进去了以后，没有执行获取最大值就出来了
        res[j++] = window.getMax();

        return res;

    }
    
    // 或者可以用下面的执行顺序
    
    public int[] maxSlidingWindow(int[] nums, int k) {
        MonoQueue window = new MonoQueue();
        int n = nums.length;
        int[] res = new int[n - k + 1];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (i < k - 1) {
                // 只放了k - 1
                // 后面就开始push找最大值了
                window.push(nums[i]);
                continue;
            } 

            //往里面放值！
            window.push(nums[i]);
            // 再找最大值
            res[j] = window.getMax();
            //维持窗口的长度，弹出窗口的第一个数
            window.pop(nums[i - k]);
            j++;
        }

        return res;

    }

}

```


`push` 操作中含有 `while` 循环，时间复杂度应该不是 `O(1)` 呀，那么本算法的时间复杂度应该不是线性时间吧？

单独看` push` 操作的复杂度确实不是` O(1)`，但是算法整体的复杂度依然是 `O(N)` 线性时间。要这样想，`nums` 中的每个元素最多被 `push` 和 `pop`一次，没有任何多余操作，所以整体的复杂度还是 `O(N)`。
