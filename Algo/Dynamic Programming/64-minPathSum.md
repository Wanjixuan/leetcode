# 题目
64. [最小路径和](https://leetcode-cn.com/problems/minimum-path-sum/)

## 思路
这道题是在 62. [不同路径](https://leetcode-cn.com/problems/unique-paths/) 的基础上，增加了路径成本概念。
更像是 硬币问题和不同路径的结合版本

我们可以根据问题来调整我们的「状态定义」：

定义 `f[i][j]` 为从 `(0,0) `开始到达位置 `(i,j)` 的最小总和。

那么 `f[m-1][n-1]` 就是我们最终的答案，`f[0][0]=grid[0][0] `是一个显而易见的起始状态。

由于题目限定了我们只能「往下」或者「往右」移动，因此我们按照 当前位置可由哪些位置转移过来 进行分析：

当前位置只能通过「往下」移动而来，即有 `f[i][j] = f[i-1][j] + grid[i][j]`

当前位置只能通过「往右」移动而来，即有 `f[i][j] = f[i][j-1] + grid[i][j]`

当前位置既能通过「往下」也能「往右」移动，即有 `f[i][j] = min(f[i][j−1],f[i−1][j])+grid[i][j]`

作者：宫水三叶
链接：https://leetcode-cn.com/leetbook/read/path-problems-in-dynamic-programming/rtb68e/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

## 代码

```java
class Solution {
    public int minPathSum(int[][] grid) {
        // 与硬币问题有点类似
        // min{到[i][j - 1]最短路径，[i - 1][j]}
        int m = grid.length, n = grid[0].length;
        int[][] minD = new int[m][n];
        minD[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            minD[i][0] = grid[i][0] + minD[i - 1][0];
        }
        for (int j = 1; j < n; j++) {
            minD[0][j] = minD[0][j - 1] + grid[0][j];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                minD[i][j] = grid[i][j] + Math.min(minD[i - 1][j],  minD[i][j - 1]);
            }
        }

        return minD[m - 1][n - 1];
    }
}
```
