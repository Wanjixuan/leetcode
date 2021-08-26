# [232. 用栈实现队列](https://leetcode-cn.com/problems/implement-queue-using-stacks/)

## 思路
队列的特点是先入先出<br/>
栈的特点是先进后出，后进先出<br/>
那么进的时候用一个栈，出的时候用一个栈！

队列的API<br/>
```java
class MyQueue {
    
    /** 添加元素到队尾 */
    public void push(int x);
    
    /** 删除队头的元素并返回 */
    public int pop();
    
    /** 返回队头元素 */
    public int peek();
    
    /** 判断队列是否为空 */
    public boolean empty();
}
```

栈的初始防止如图
![栈组队列](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/223.png)


### push
把元素压进队列的时候，只需要push进S1就可以
```java
/** 添加元素到队尾 */
public void push(int x) {
    s1.push(x);
}
```

### pop和peek
如果有了元素了，如何查看队列`peek()`的元素呢？<br/>
这里和`pop`考虑的问题一样，S2负责出，那么S2的栈顶元素才是队列的`peek()`.<br/>
我是压进了 S1，我怎么才能拿到 S2的栈顶元素？？
- S2不为空，直接输出S2的栈顶元素就是队列的peek()
- S2为空，那就把S1那部分**全部**拿到S2里面，经过了一次先进后出，到S2是不是就是后进了，那就先出，和最开始放进来的顺序一样！


```java

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        peek();
        return s2.pop();
    }
    
    /** Get the front element. */
    public int peek() {
        if (s2.isEmpty()) {
            // 把s1中的元素全部放到s2中
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        } 
        return s2.peek();
    }
```

操作如下图：

**push和peek():**
**里面确认了S2是不是空，但是没截进去！**

![peek\push](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/232-peek-push.gif)

<br/>
<br/>
<br/>
**pop:**
![pop](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/232-popPart.gif)

<br/>
### isEmpty

要看队列是否为空，只需要看两个栈里面还有没有元素！
```java
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        return s1.isEmpty() && s2.isEmpty();
    }

```

