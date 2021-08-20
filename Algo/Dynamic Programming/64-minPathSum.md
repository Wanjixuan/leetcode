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

## 优化代码

```java
        // 与硬币问题有点类似
        // min{到[i][j - 1]最短路径，[i - 1][j]}
        int m = grid.length, n = grid[0].length;

        int[] f = new int[n];
        // 初始条件
        f[0] = grid[0][0];
        // 边界条件，可以做累加，

        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i - 1 >= 0 && j - 1 >= 0) {
                    f[j] = Math.min(f[j], f[j - 1]) + grid[i][j];
                } 
                //边界条件包括 横向和纵向，都需要处理
                if (i == 0 && j - 1 >= 0) {
                    // 第一行
                    f[j] = f[j - 1] + grid[0][j];
                }
                if (j == 0 && i - 1 >= 0) {
                    // 第一列
                    f[j] = grid[i][0] + f[0];
                }
            }
        }

        return f[n - 1];
```



# 进阶
将最短路径途径的点给输出来（如果有多个，输出一个就可以）
从原问题我们知道，我们需要从 (0,0(0,0) 一步步转移到 (m-1,n-1)(m−1,n−1)。

也就是我们需要扫描完整个方块（转移完所有的状态），才能得到答案。

那么显然，我们可以使用额外的数据结构来记录，我们是如何一步步转移到 f[m-1][n-1]f[m−1][n−1] 的。

当整个 DP 过程结束后，我们再用辅助记录的数据结构来回推我们的路径。

同时，由于我们原有的 DP 部分已经创建了一个二维数组来存储状态值，这次用于记录「上一步」的 g 数组我们改用一维数组来记录。

维数为 `m*n`的二维数组，转换为一维数组：<br/>
`f[m*n]`: `f[i][j]` 对应的一维下标 `idx = i * n + j`

```java
class Solution {
    int m, n;
    public int minPathSum(int[][] grid) {        
        m = grid.length;
        n = grid[0].length;
        int[][] f = new int[m][n];
        int[] g = new int[m * n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    f[i][j] = grid[i][j];
                } else {
                    int top  = i - 1 >= 0 ? f[i - 1][j] + grid[i][j] : Integer.MAX_VALUE;
                    int left = j - 1 >= 0 ? f[i][j - 1] + grid[i][j] : Integer.MAX_VALUE;
                    f[i][j] = Math.min(top, left);
                    // 这里把当前行某个点最短路径的上一级路径存到当前行代表的一维数组中
                    // 就是我代表一个下标，我这个下标里存的是上一级中让我到最短路径的那位下标
                    g[getIdx(i, j)] = top < left ? getIdx(i - 1, j) : getIdx(i, j - 1);
                }
            }
        }
        
        // 从「结尾」开始，在 g[] 数组中找「上一步」
        int idx = getIdx(m - 1, n - 1);
        // 逆序将路径点添加到 path 数组中
        int[][] path = new int[m + n][2];
        path[m + n - 1] = new int[]{m - 1, n - 1};
        for (int i = 1; i < m + n; i++) {
            path[m + n - 1 - i] = parseIdx(g[idx]);
            idx = g[idx];
        }
        // 顺序输出位置
        for (int i = 1; i < m + n; i++) {
            int x = path[i][0], y = path[i][1];
            System.out.print("(" + x + "," + y + ") ");
        }
        System.out.println(" ");
        
        return f[m - 1][n - 1];
    }
    int[] parseIdx(int idx) {
        return new int[]{idx / n, idx % n};
    }
    int getIdx(int x, int y) {
        return x * n + y;
    }
}

```


也许你会觉得「输出」方案的代码太麻烦了。

这是因为我们找路径的过程是「倒着」找，而输出方案的时候则是「顺着」输出。

如果希望简化找路径的过程，我们需要对原问题进行等价转换：

将 「`(0,0)` 到 `(m−1,n−1`) 的最短路径」转换为「从 `(m−1,n−1)` 到 `(0,0)` 的最短路径」，同时移动方向从「向下 & 向右」转换为「向上 & 向左」。

这样我们就能实现「找路径」的顺序和「输出」顺序同向。

调整定义 `f[i][j]`为从 `(m−1,n−1)` 开始到达位置`(i,j)`的最小总和。

![一维到二维](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/64-1.png)


```java
class Solution {
    int m, n;
    public int minPathSum(int[][] grid) {        
        m = grid.length;
        n = grid[0].length;
        int[][] f = new int[m][n];
        int[] g = new int[m * n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 && j == n - 1) {
                    f[i][j] = grid[i][j];
                } else {
                    int bottom = i + 1 < m ? f[i + 1][j] + grid[i][j] : Integer.MAX_VALUE;
                    int right  = j + 1 < n ? f[i][j + 1] + grid[i][j] : Integer.MAX_VALUE; 
                    f[i][j] = Math.min(bottom, right);
                    g[getIdx(i, j)] = bottom < right ? getIdx(i + 1, j) : getIdx(i, j + 1);
                }
            }
        }

        int idx = getIdx(0,0);
        for (int i = 1; i <= m + n; i++) {
            if (i == m + n) continue;
            int x = parseIdx(idx)[0], y = parseIdx(idx)[1];
            System.out.print("(" + x + "," + y + ") ");
            idx = g[idx];
        }
        System.out.println(" ");

        return f[0][0];
    }
    int[] parseIdx(int idx) {
        return new int[]{idx / n, idx % n};
    }
    int getIdx(int x, int y) {
        return x * n + y;
    }
}

```

- 上述存储倒序寻址的示意如下
- ![寻址](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/64-2.png)


如果方块中「存在负权」，如何求解？ <br/>
如果只是增加负权值的条件，走动规则不变（只能往下或往右），那么 DP 仍然有效。仍然能够得到「总成本最小」的路径，但不确保成本必然为负权，也不确保必然会经过负权位置。 <br/>

如果走动规则调整为「可以往任意方向」且「每个位置最多只能访问一次」，如何求解？ <br/>
这时候问题就转换为「图论最短路」问题，而且是从「特定源点」到「特定汇点」的「单源最短路」问题。 <br/>

需要根据是否存在「负权边」来分情况讨论：

- 不存在负权边：使用 Dijkstra 算法求解
- 存在负权边：使用 Bellman Ford 或 SPFA 求解





作者：宫水三叶
链接：https://leetcode-cn.com/leetbook/read/path-problems-in-dynamic-programming/rtb68e/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

