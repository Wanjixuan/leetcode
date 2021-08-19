# 1289.[ 下降路径最小和  II](https://leetcode-cn.com/problems/minimum-falling-path-sum-ii/)

## 思路：

现在是上一行只有一个限制，不能是同列，其余值都可以取！<br/>

首先明确，是两行之间不能选择同一列，但如果上一行最小值恰好和我同一列呢？那只能取次小值  <br/>

并且，为了得到全局最优化，时间复杂度 O(n^2)起步   <br/>

如果按照暴力法，每次遍历，先遍历上一行找到最小值和次小值，那么这样会使得复杂度到 O(n^3)   <br/>



暴力法：

```java
    public int minFallingPathSum(int[][] grid) {
        int n = grid.length;
        int[][] f = new int[n][n];
        // 初始化首行的路径和
        for (int i = 0; i < n; i++) f[0][i] = grid[0][i];
        // 从第一行进行转移
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                f[i][j] = Integer.MAX_VALUE;
                int val = grid[i][j];
                // 枚举上一行的每个列下标
                for (int p = 0; p < n; p++) {
                    // 只有列下标不同时，才能更新状态
                    if (j != p) {
                        f[i][j] = Math.min(f[i][j], f[i-1][p] + val);
                    }
                }
            }
        }
        // 在所有的 f[n - 1][i] 中取最小值
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, f[n-1][i]);
        }
        return ans;

    }
```



时间复杂度：O(n^3)

空间复杂度：O(n^2)



## 优化1：

两个点

- DP 状态转移部分，共有 `n * n` 个状态需要转移
  - 为得到全局最优，这个无法避免
- 每次转移的时候，枚举上一行的所有列
  - 对于上一行，我只需要知道最小值和次小值下标

这里介绍一个找最小值与次小值下标的方法  <br/>

![找最小值方法](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/1289.png)



```java
    public int minFallingPathSum(int[][] grid) {
        int n = grid.length;

        // 下一行取值只与上一行的最小或者第二小的值有关。
        // 因此想办法保存上一行最小值与第二小值的row、col、val信息；
        int Mini = -1, Seci = -1;
        int MAX = Integer.MAX_VALUE;
        int[][] f = new int[n][n];
        for (int i = 0; i < n; i++) {
            // 先处理第一行
            // 同时初始化
            int val = grid[0][i];
            f[0][i] = val;

            if (val < (Mini == -1 ? MAX : f[0][Mini])) {
                // 找到比最小的更小的
                Seci = Mini;
                Mini = i;
            } else if (val < (Seci == -1 ? MAX : f[0][Seci])) {
                //不比最小的小，那就比第二小的
                //找到比第二小的了
                Seci = i;
            }
        }

        for (int i = 1; i < n; i++) {
            int t1 = -1, t2 = -1;
            // 下面每行相加找最小的
            for (int j = 0; j < n; j++) {
                int tmp = grid[i][j];
                f[i][j] = MAX;

                //下面分情况，可能这时候我不能取最小值
                if (j != Mini) {
                    //这是能取最小值
                    f[i][j] = f[i - 1][Mini] + tmp;
                }

                if (j == Mini) {
                    // 取次小值
                    f[i][j] = f[i - 1][Seci] + tmp;
                }
                // 该进行本行的比较,得出累计到本行时，
                if (f[i][j] < (t1 == -1 ? MAX : f[i][t1])) {
                    t2 = t1;
                    t1 = j;
                } else if (f[i][j] < (t2 == -1 ? MAX : f[i][t2])) {
                    t2 = j;
                }
            }

            // 用临时变量更新
            Mini = t1;
            Seci = t2;

        }

        // 在所有的 f[n - 1][i] 中取最小值
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, f[n-1][i]);
        }
        return ans;

    }
```

时间复杂度：O(n^2)

空间复杂度：O(n^2)



## 优化2：

空间压缩，不需要这一行的所有值  <br/>

- `first_sum` 表示这一行的最小值；
- `first_pos` 表示这一行最小值对应的 `jmin`；
- `second_sum` 表示这一行的次小值。
- 为什么没有次小值所在的下标？因为之所以存在次小值是因为有可能最小值和当前数同列，**所以我得知道是不是同列** !如果不同列，那我直接取次小值不就行了！

```java
    public int minFallingPathSum(int[][] grid) {
        int n = grid.length;

        // 下一行取值只与上一行的最小或者第二小的值有关。
        // 因此想办法保存上一行最小值与第二小值的row、col、val信息；
        int MinVal = 0, MinPos = -1, SecVal = 0;
        int MAX = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            // 用来搜索每行最小值和次小值的临时变量
            int tmin = MAX, tpos = -1, tsec = MAX;
            for (int j = 0; j < n; j++) {
                
                int cur_sum = (MinPos == j ? SecVal : MinVal) + grid[i][j];
                if (cur_sum < tmin) {
                    // 最小
                    tpos = j;
                    tsec = tmin;
                    tmin = cur_sum;

                } else if (cur_sum < tsec){
                    // 次小
                    tsec = cur_sum;
                }
            } 
            // 更新
            MinVal = tmin;
            MinPos = tpos;
            SecVal = tsec;
        }
        return MinVal;
    }
```
