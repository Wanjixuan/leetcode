### 解题思路
借鉴东哥的博客。
思路：https://labuladong.gitee.io/algo/2/20/39/
关键在于，将一个有序序列，拆分成一个小堆顶、一个大堆顶。
获取中位数也就变成了获取堆顶元素
- 偶数的时候，两个队列大小一样，取两个堆顶元素的平均值
- 奇数的时候，总有一个队列是大的，取其堆顶元素便可！


将数字添加进序列中：
接下来最精妙之处在于处理两个堆顶元素的大小关系不乱这部分！！
![image.png](https://pic.leetcode-cn.com/1628751282-OcAZmt-image.png)
思路就是，**插入的过程中保证堆顶元素的连续，假设heap1数量小于heap2，这时候要插入heap1，但让其中要本该插入的一个堆heap1是接受另一个堆heap2的堆顶元素，不直接接受插入的数据num**
- 因为插入之前，即使两者相差1，你选择了多的那方，转移堆顶元素后，差值还是1，相等就更别说了
- 一旦插入更少的那组，比较起来就十分麻烦，每次都需要比较堆顶元素的大小
- 假设我们准备向minpq中插入元素：如果插入的num小于maxpq的堆顶元素，那么num就会留在maxpq堆里，为了保证两个堆的元素数量之差不大于 1，作为交换，把maxpq堆顶部的元素再插到minpq堆里。


### 代码

```java
class MedianFinder {

    PriorityQueue<Integer> maxpq;

    PriorityQueue<Integer> minpq;
    /** initialize your data structure here. */
    public MedianFinder() {
        // 大堆顶，要让它降序排列，取顶的时候才是最大值, 里面都是挺小的数
        maxpq = new PriorityQueue<>(((o1, o2) -> {return o2 - o1;}));

        // 小堆顶，默认的，升序排列，取顶的时候是最小值，里面都是挺大的数
        minpq = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        // 难点在于保证两个堆顶的大小关系不能乱
        if (maxpq.size() >= minpq.size()) {
            // 目的是放进比较少的那个
            // 但是先放进多的那个，他们总是相差1
            maxpq.offer(num);
            minpq.offer(maxpq.poll());
        } else {
            minpq.offer(num);
            maxpq.offer(minpq.poll());
        }

    }
    
    public double findMedian() {
        if (minpq.size() > maxpq.size()) {
            // 如果两边数目不一样多，那么数量多的那边顶是中位数
            return minpq.peek();
        } else if (minpq.size() < maxpq.size()) {

            return maxpq.peek();
        }
        return (minpq.peek() + maxpq.peek()) / 2.0;
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
```
