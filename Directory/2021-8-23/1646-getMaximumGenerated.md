 [1646. 获取生成数组中的最大值](https://leetcode-cn.com/problems/get-maximum-in-generated-array/)
 
 看规律可以知道，不一定最后的就是最大的，因此必须全部求出来才好求最大值 <br/>
 
按照给定的公式
- 2*i < n : num[2*i] = num[i];
- 2*i + 1 < n : num[2*i+1] = num[i+1]+num[i];

两种分析:
- 求出的是 i
-   这样就需要知道i的属性
![推导](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/1646.png)
- 求出的是 2*i 或者 2*i + 1
-   这种就直接按照公式写if判断语句
 
 第一种分析：
 ```java
    public int getMaximumGenerated(int n) {
        if (n == 0) return 0;

        int[] arr = new int[n + 1];

        arr[1] = 1;

        for (int i = 2; i <= n; i++) {
            arr[i] = arr[i / 2] + i % 2*arr[i / 2 + 1];
        }

        int ans = 0;
        for (int i : arr) ans = Math.max(ans, i);
        return ans;
    }
    ```
    
    第二种分析：
    ```java
     public int getMaximumGenerated(int N) {
        if (N == 0) return 0;
        num[0] = 0;
        num[1] = 1;

        for (int i = 0; i < N; i++) {
            if (2*i < N) num[2*i] =num[i];
            if (2*i + 1 < N) num[2*i+1] = num[i] + num[i + 1];
        }

        int max = 0;
        for (int i = 0; i < N; i++) {
            max = Math.max(num[i], max);
            ans[i] = max;
        }

        int ans = 0;
        for (int i : arr) ans = Math.max(ans, i);
        return ans;
    }
```
    
还可以利用静态，在执行程序时，不把最耗时的算在我们程序中，多谢三叶大佬的解法：
```java
class Solution {
    static int N = 110;
    static int[] num = new int[N];
    static int[] ans = new int[N];

    static {
        num[0] = 0;
        num[1] = 1;

        for (int i = 0; i < N; i++) {
            if (2*i < N) num[2*i] =num[i];
            if (2*i + 1 < N) num[2*i+1] = num[i] + num[i + 1];
        }

        int max = 0;
        for (int i = 0; i < N; i++) {
            max = Math.max(num[i], max);
            ans[i] = max;
        }
    }
    public int getMaximumGenerated(int n) {
        return ans[n];
    }
}
```
    
