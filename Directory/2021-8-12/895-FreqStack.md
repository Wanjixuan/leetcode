### 解题思路
首先分析：
- 会涉及元素出现的频繁（频率)问题，需要记录每个元素出现的频率
- 同频率出现的元素，移除靠近栈顶的——后入先出，也就是一个栈
- 移除最频繁的元素，表示需要存储当前的最高频率

针对上面三个分析，分别给出方案
- 一个数据结构能从元素，映射到它的出现频率； HashMap, Key: val, Value: freq.  简称VF表
- 对每个频率，都有一个存储多个元素，并且满足**后入先出**的顺序；HashMap, Key: freq, Value: Stack<Integer>
- 设置一个maxFreq

关于push函数：
![image.png](https://pic.leetcode-cn.com/1628740923-faIJTV-image.png)

- 这里有个问题需要注意：
-   并不需要把原来栈里面的val给弹出来，执行完pop操作后，freq-1，到时候还是按照距离栈顶的顺序来


关于 pop():
![image.png](https://pic.leetcode-cn.com/1628741591-QowYoF-image.png)



### 代码

```java

class FreqStack {
    int maxFreq = 0;

    // 记录每个元素对应的 freq  VF表
    HashMap<Integer, Integer> ValToFreq;

    // 同频率的时候，先弹出最新的，也就是后入先出，栈   FV表
    HashMap<Integer, Stack<Integer>> FreqToVals;

    public FreqStack() {
        ValToFreq = new HashMap<>();
        FreqToVals = new HashMap<>();

    }
    
    public void push(int val) {
        // change ValToFreq
        int freq = ValToFreq.getOrDefault(val, 0) + 1;
        ValToFreq.put(val, freq);

        // change FreqToVals
        FreqToVals.putIfAbsent(freq, new Stack<Integer>());
        FreqToVals.get(freq).push(val);

        // change maxFreq
        maxFreq = Math.max(freq, maxFreq);

        // 有个问题，原来的栈里面的val需要弹出来吗？？
        // 不用，执行完pop操作后，freq-1，到时候还是按照距离栈顶的顺序来
    }
    
    public int pop() {
        // get freq stack
        Stack<Integer> tmp = FreqToVals.get(maxFreq);
        // change FV
        // get the num
        int topNum = tmp.pop();
        if (tmp.isEmpty()) {
            FreqToVals.remove(maxFreq);
            // update maxFreq
            maxFreq--;
        }
        // change VF
        int freq = ValToFreq.get(topNum);
        if (freq-- == 0) {
            ValToFreq.remove(topNum);
        } else {
            ValToFreq.put(topNum, freq--);
        }

        return topNum;


    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */
```
