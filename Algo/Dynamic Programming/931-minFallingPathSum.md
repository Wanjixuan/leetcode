# 931. [下降路径最小和](https://leetcode-cn.com/problems/minimum-falling-path-sum/)

## 思路：

与最简单路径问题不同的是，不再是从左上角出发 <br/>
120.[ 三角形最小路径和](https://leetcode-cn.com/problems/triangle/) 是被迫左上角下来，只要到最后一行任意位置都可以  <br/>
本题的话，可以从第一行的任意位置出发，到最后一行的任意位置都可以。<br/>

初始条件:
- 自顶向下
-   第一行的元素就是初始条件
- 自底向上
-   最后一行的元素就是初始条件

边界条件：
通过观察可以发现以下性质（令` i `为行坐标，`j` 为列坐标）：


只要不是第一列（`j!=0`）位置上的数，都能通过「左上方」,「正上方」转移过来 <br/>
只要不是每行最后一列（`j!=i`）位置上的数，都能通过「右上方」， 「正上方」转移而来 <br/>
意味着，无论如何，都能从「正上方」获取到一个参考值。因此只需要把「正上方」与「左上方」、「右上方」进行比较就可以了 （这其实就是自底向上的思路了）

反推的话，就是从下一行中的 正下、右下、左下，找一个最小值（这是自顶向下的思路）







第一版：无论是时间复杂度还是空间复杂度都很高，时间复杂度因为进行了多次的`Math.min`

```java
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int[][] f = new int[n][n];

        f[0] = matrix[0];

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = matrix[i][j];
                f[i][j] = Integer.MAX_VALUE;
                if (j - 1 >= 0) {
                    // 避免在最左边出界,假设在最右边，只用考虑正上方与左上方
                    f[i][j] = Math.min(f[i][j], Math.min(f[i - 1][j], f[i - 1][j - 1]) + val);
                } 
                if (j + 1 < n) {
                    // 避免在最右边出界,假设在最左边，最考虑正上方和右上方
                    f[i][j] = Math.min(f[i][j], Math.min(f[i - 1][j], f[i - 1][j + 1]) + val);

                }
            }
        }

        int retMin = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            retMin = Math.min(retMin, f[n - 1][i]);
        }

        return retMin;
        
    }
```



## 优化1：

需要注意的是，从上往下的过程中，上一行的最小值一定是已经存到了我们自己建的数组里面

```java
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int[][] f = new int[n][n];

        for (int i = 0; i < n; i++) {
            f[0][i] = matrix[0][i];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 正上方一定可以取到
                int lastBest = f[i - 1][j];
                if (j - 1 >= 0) {
                    // 避免在最左边出界,假设在最右边，只用考虑正上方与左上方
                    lastBest = Math.min(lastBest, f[i - 1][j - 1]);
                } 
                if (j + 1 < n) {
                    // 避免在最右边出界,假设在最左边，最考虑正上方和右上方
                    lastBest = Math.min(lastBest, f[i - 1][j + 1]);

                }
                //得到下一行最小的了，相加
                f[i][j] = lastBest + matrix[i][j];
            }
        }
        int retMin = f[n - 1][0];
        for (int i = 1; i < n; i++) {
            retMin = Math.min(retMin, f[n - 1][i]);
        }
        return retMin;
        
    }
```







## 优化2：

直接在原数组上进行修改，这样可以节省大量空间

**自底向上的修改**  

就像是爬楼梯一样

```java
    

public int minFallingPathSum(int[][] matrix) {
        int N = matrix.length;

        for (int i = N - 2; i >= 0; i--) {
            for (int j = 0;j < N; j++) {
                
                // 不管在哪个边界，我总能去到我自己下面的那个
                int best = matrix[i + 1][j];
                if (j - 1 >= 0)  {
                    // 防止在最左边，那就以最右边为例， 只能是最下和左下
                    best = Math.min(best, matrix[i + 1][j - 1]);
                }
                if (j + 1 < N) {
                    // 防止在最右边，那就以最左边对比, 只能是最下面，和右下的
                    best = Math.min(best, matrix[i + 1][j + 1]);
                }
                // 现在得到了下一列最小的，
                matrix[i][j] += best;
            }
        }

        int retMin = matrix[0][0];
        for (int i = 1; i < N; i++) {
            retMin = Math.min(retMin, matrix[0][i]);
        }
        return retMin;
        
    }

```



**自顶向下的修改**

```java
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;


        // 尝试修改原数组
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 正上方一定可以取到
                int lastMin = matrix[i - 1][j];
                if (j - 1 >= 0) {
                    // 避免在最左边出界,假设在最右边，只用考虑正上方与左上方
                    lastMin = Math.min(lastMin, matrix[i - 1][j - 1]);
                } 
                if (j + 1 < n) {
                    // 避免在最右边出界,假设在最左边，最考虑正上方和右上方
                    lastMin = Math.min(lastMin, matrix[i - 1][j + 1]);

                }
                //得到下一行最小的了，相加
                matrix[i][j] = lastMin + matrix[i][j];
            }
        }

        int retMin = matrix[n - 1][0];
        for (int i = 1; i < n; i++) {
            retMin = Math.min(retMin, matrix[n - 1][i]);
        }

        return retMin;
        
    }
```
