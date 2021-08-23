来自东哥的模板<br/>



## 倒序入栈
这是栈底大，栈顶小的单调栈
```java
int[] nextGreaterElement(int[] nums) {

    int[] res = new int[nums.length]; // 存放答案的数组
    Stack<Integer> s = new Stack<>();
    // 倒着往栈里放
    for (int i = nums.size() - 1; i >= 0; i--) {
        // 判定个子高矮
        while (!s.isEmpty() && s.peek() <= nums[i]) {
            // 矮个起开，反正也被挡着了。。。
            s.pop();
        }
        // nums[i] 身后的 next great number
        res[i] = s.isEmpty() ? -1 : s.peek();
        s.push(nums[i]);
    }
    return res;
}
```

## 顺序入栈
栈底小，栈顶大，所以一pop，就存进数组里面
```java
    public int[] nextGreaterElement(int[] nums) {

        if (nums == null ) {
            return null;
        }

        //创建一个数组来接收
        int[] ret = new int[nums.length];
        Deque<Integer> s = new LinkedList<>();
        int n = nums.length;
        


        for (int i = 0; i < n ; i++) {
            while (!s.isEmpty() && nums[s.peek()] <= nums[i]) {
                ret[s.pop()] = nums[i];
            }
          // 也可以提前设定值，因为要改变的只有满足后面有最大值的
            if (s.isEmpty()) ret[i] = -1;
   
            s.push(i);
        }
        return ret;

    }
```
