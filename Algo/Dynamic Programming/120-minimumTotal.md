# 120.[ 三角形最小路径和](https://leetcode-cn.com/problems/triangle/)

## 思路：

需要注意两个点

![示意图](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/120.png)

- 每行的元素个数等于当前行数+1  ：` Len(j)  = i + 1`

- 对角线上的点（`j  =  i`）（蓝色）只能通过左上方的下来
- 第一列的点(`j = 0`) (绿色)只能通过正上方下来
- 并且是要求最后一行的最小就可以，并不是最右下角的点



```java
// 这不是我想到的，但是这种从最后一行开始递推的思想，值得去思考，从最后一行到顶点的最短路径
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        // dp[i][j] 表示从点 (i, j) 到底边的最小路径和。
        int[][] dp = new int[n + 1][n + 1];
        // 从三角形的最后一行开始递推。
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0][0];
    }
}


```





```java
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int LastJ = 0;
        int maxLen = triangle.get(n - 1).size();

        int[][] f = new int[n][n];

        f[0][0] = triangle.get(0).get(0);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i + 1; j++) {
                int val = triangle.get(i).get(j);
                // 条件判断
                if (j - 1 >= 0) {
                    f[i][j] = Math.min(f[i - 1][j], f[i - 1][j - 1]) + val;
                } else if (j == 0) {
                    f[i][j] = val + f[i - 1][j];
                } if (j == i) {
                    f[i][j] = val + f[i - 1][j - 1];
                }
            }

        }
        int retMin = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            retMin = Math.min(f[n - 1][i], retMin);
        }
        return retMin;
    }
```





化简版本代码：

如果不属于对角线和第一列的点，需要比较两次求和的最小值

如果属于，均需要计算一次最小值，并且这两个取交集就是不属于对角线需要进行的操作。

```java
        

		for (int i = 1; i < n; i++) {
            for (int j = 0; j < i + 1; j++) {
                int val = triangle.get(i).get(j);
                f[i][j] = Integer.MAX_VALUE;
                
                if (j != i ) f[i][j] = Math.min(f[i][j], f[i - 1][j] + val);
                if (j != 0 ) f[i][j] = Math.min(f[i][j], f[i - 1][j - 1] + val);
            }

        }
```

上述两种代码

**时间复杂度（O(n^2)）**

**空间复杂度（O(n^2))**



## 优化：

原理：当前 i，j只与前面的最多两个状态相关，不需要整个数组，后序的时候前面的数组都是没用的。



两种优化方案：

- 用两行，一行存储上一行已经求出结果的，另一行边计算边存当前行的结果，复杂度 2n
- 用一行，但是遍历的时候需要倒序遍历
  - 这是很重要的思想，当前已经是 i，j了，如果继续累加遍历，那么当前行，当前点之前的数组就无法更新了

```java
   public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] f = new int[n];

        // 初始条件
        f[0] = triangle.get(0).get(0);

        // 进行空间优化
        for (int i = 1; i < n; i++) {
            // 求出每一行最后一个的最优，再递减的调整前面的
            f[i] = f[i - 1] + triangle.get(i).get(i);
            for (int j = i - 1; j > 0; j--) {
                f[j] = Math.min(f[j], f[j - 1]) + triangle.get(i).get(j);
            }
            // 第一个点无法更新
            // 手动更新
            f[0] += triangle.get(i).get(0);
        }

        int retMin = f[0];
        for (int i = 1; i < n; i++) {
            retMin = Math.min(f[i], retMin);
        } 

        return retMin;

    }

```
