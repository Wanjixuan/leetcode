# [496. 下一个更大元素 I](https://leetcode-cn.com/problems/next-greater-element-i/)

## 思路
现在 nums2里面，找好这种比自己大之类的条件，然后在nums[2]中找到nums[1]对应的**值**，取出。

利用最小栈，把中间不满足条件（比自己大）的全部弹出栈，可以选择从后往前、从前往后
- 从前到后：是一个栈底小，栈顶大的，总不能一次性就比到最大的那个，所以去和栈底比
- 从后到前：是一个栈底大，栈顶小的，每次都和栈顶比

不能和最大的那个比。
这样就要求两边出，所以从前到后要用队列，Deque；


### 从前往后
一个栈底小，栈顶大的栈。
但是效果和从后到前一样，这样和栈底比，从后到前的话是和栈顶比
```java
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;

        Deque<Integer> stack = new ArrayDeque<>();
        Map<Integer, Integer> map = new HashMap<>();
        // 先处理 nums2，把对应关系存入哈希表
        for (int i = 0; i < len2; i++) {
            // 和栈底比，如果我比栈底大，说明我这个是栈底的后一个最大值
            while (!stack.isEmpty() && stack.peekLast() < nums2[i]) {
                map.put(stack.removeLast(), nums2[i]);
            }
            stack.addLast(nums2[i]);
        }

        // 遍历 nums1 得到结果集
        int[] res = new int[len1];
        for (int i = 0; i < len1; i++) {
            res[i] = map.getOrDefault(nums1[i], -1);
        }
        return res;        
    }

```

### 从后往前
```java
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> s = new Stack<>();
        // 前面存值,后面存下标
        Map<Integer, Integer> map = new HashMap<>();
        int[] res = new int[nums2.length];
        for (int i = nums2.length - 1; i >= 0; i--) {
            while(!s.isEmpty() && s.peek() <= nums2[i]) {
                s.pop();
            }
            res[i] = s.isEmpty() ? -1 : s.peek();
            map.put(nums2[i],res[i]);
            s.push(nums2[i]);            
        }
        for (int j = 0; j < nums1.length; j++) {
            nums1[j] = map.get(nums1[j]);
        }
        return nums1;
        
    }
```


# [503. 下一个更大元素 II](https://leetcode-cn.com/problems/next-greater-element-ii/)

## 思路：
在最基础的单调栈问题上加了个数组可循环
- 我必须能重复访问数组，就是从头访问到尾-->头到尾。这样可以把所有数的最大值都找到
- 我存我后面最大值（返回的数组）和原数组大小一样

两种解决方法：
- 把数组翻倍，用一个for循环，填补后面的数组。然后再像上一题一样
- 模拟长度翻倍了，把遍历的长度扩展到两倍，但是数组在存取的时候进行 `i % n` 的操作

### 第一种方法：数组翻倍
- 这里是从前往后，并且是和栈顶的元素比。
- 一直存一直存，满足条件的时候是，我比栈顶元素大，栈顶是后进去的，是离我最近的！
```java
    public int[] nextGreaterElements(int[] nums) {

        if (nums == null ) {
            return null;
        }
        int n = nums.length;
        //
        int[] copy = new int[n*2];

        for (int i = 0; i < copy.length; i++) {
            copy[i] = nums[i % n];
        }

        Stack<Integer> stack = new Stack<>();
        // 存下标，比我大的值的下标
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < copy.length; i++) {
          //当前的我，比栈顶要大，我就是栈顶元素的后一个最大值。
         
            while (!stack.isEmpty() && copy[i] > copy[stack.peek()]) {
                Integer pop = stack.pop();

                map.put(pop, i);
            }
            stack.push(i);
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = map.getOrDefault(i, -1) == -1 ? -1 : copy[map.get(i)];
        }

        return nums;

    }
    
```

### 第二种方法：模拟翻倍
```java
    public int[] nextGreaterElements(int[] nums) {

        int n = nums.length;

        Stack<Integer> s = new Stack();
        int[] res = new int[n];
        for (int i = 2 * n - 1; i >= 0; i--) {

            while (!s.isEmpty() && s.peek() <= nums[i % n]) {
                s.pop();
            }

            // 找到比我大的了，该存进去
            res[i % n] = s.isEmpty() ? -1 : s.peek();
            s.push(nums[i % n]);
        }

        return res;   
    }

```



# [739. 每日温度](https://leetcode-cn.com/problems/daily-temperatures/)

## 思路：
除了要找到后一个最大值，更重要的判断天数，但是单调栈是会把中间数给弹出去的，会出错
如果想知道天数，而天数又是数组下标之差，所以可以直接存下标，但是比的时候把下标带到数组里面去。



## 代码

```java
    public int[] dailyTemperatures(int[] T) {
        
        int n = T.length;
        int[] res = new int[n];
        // 这个单调栈是栈顶元素小于栈底
        // 因为我是需要知道坐标差，因此我把坐标给压入栈
        // 为什么不能用值，因为我中途把比其他元素小得给弹出来了，影响之后的结果
        // 不管多小，他都算一个距离.
        Stack<Integer> s = new Stack<>();
        int tmp = 0;
        for (int i = n - 1; i>= 0; i--) {
            // 开始倒着遍历
            while (!s.isEmpty() && T[s.peek()] <= T[i]) {
                // 在数组还有元素，同时栈顶的元素
                s.pop();
            }
            // 找到比我大的了,我也通过tmp知道勒，我们之间有多少人
            res[i] = s.isEmpty() ? 0 : s.peek() - i;
            // 处理完我了，该把我压入栈，为数组前面的进行操作了
            s.push(i);
        
        }

        return res;
    }
```
