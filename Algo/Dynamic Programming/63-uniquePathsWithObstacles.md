# 题目
63. [不同路径 II](https://leetcode-cn.com/problems/unique-paths-ii/)


## 思路：

这道题和 62. 不同路径 相比，多了某些格子有障碍物（不可达）的限制，但丝毫不影响我们的分析。

还是定义`f[i][j] `为到达位置` (i,j)` 的不同路径数量。

那么 `f[m−1][n−1]` 就是我们最终的答案。

`f[0][0] = 1` 是一个显而易见的起始条件，同时由于某些格子上有障碍物，对于 `grid[i][j]==1` 的格子，则有 `f[i][j] = 0`。

由于题目限定了我们只能「往下」或者「往右」移动，因此我们按照当前可选方向进行分析：

当前位置只能「往下」移动，即有 `f[i][j] = f[i-1][j]`

当前位置只能「往右」移动，即有 `f[i][j] = f[i][j-1]`

当前位置即能「往下」也能「往右」移动，即有` f[i][j] = f[i][j-1] + f[i-1][j]`

作者：宫水三叶
链接：https://leetcode-cn.com/leetbook/read/path-problems-in-dynamic-programming/rt1bk2/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

## 代码
### 基础 DP
```java
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        
        int m = obstacleGrid.length, n = obstacleGrid[0].length;

        int[][] distance = new int[m+1][n+1];

        
        // 这句意义在哪里？？真的会在出发点设置障碍？
        distance[0][0] = obstacleGrid[0][0] == 0 ? 1 : 0;
        // distance[0][0] = 1; 真的会出现障碍放在起始点，那么意味着整个都应该是0了。
        // 起始点为0，首行首列都是0，所以后序也都是0

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 所有的遍历，前提都是当前无障碍
                //至于我担心的，i-1或者j-1是障碍，如果这样，那么他们都访问不到，更别说修改值，他们就一直是1，所以等式里也就是固定的
                
                if (obstacleGrid[i][j] != 1) {
                    if (i > 0 && j > 0) {
                        distance[i][j] = distance[i - 1][j] + distance[i][j - 1];
                    } else if (i > 0) {
                        // j=0 竖着走
                        distance[i][j] = distance[i - 1][j];

                    } else if (j > 0) {
                        // i=0 
                        distance[i][j] = distance[i][j - 1];
                    }                 
                }
            }
        }
        return distance[m - 1][n - 1];
        
    }
```

### 优化：滚动数组降维
可以简化，时间复杂度可以降低，因为如果此列的i位置出现障碍，那么 i 之后的数据全部用不上。因为只能向右或向下。

这时候可以选择跳过后面的程序，行也是一样的

#### 行的写法：

```java
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        //  可以简化，时间复杂度可以降低，因为如果此列的i位置出现障碍，那么i之后的数据全部用不上。因为只能向右或向下

        // 同理，换成某行也是一样的
        //

        int m = obstacleGrid.length, n = obstacleGrid[0].length;

        int[] f = new int[n];

        f[0] = obstacleGrid[0][0] == 0 ? 1 : 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(obstacleGrid[i][j] == 1) {
                    f[j] = 0;
                    continue;
                }
                // j - 1 是为了行遍历的时候除掉开头
                // 因为根据循环的顺序，会先遍历到[i][j-1],所以不用考虑横向的前面那是是不是有问题，需要考虑的是上面那个 
                // 答案的  [i][j-1] == 0 (不是障碍)后面那部分判断完全是多余的，但是这样可以告诉，前面那个不是障碍才行
                //if (j - 1 >= 0 && obstacleGrid[i][j-1] == 0) {
                if (j - 1 >= 0) {
                    f[j] += f[j - 1];
                }
            }
        }

        return f[n - 1];
        
    }
```



#### 列的写法：

```java
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        //  可以简化，时间复杂度可以降低，因为如果此列的i位置出现障碍，那么i之后的数据全部用不上。因为只能向右或向下


        int m = obstacleGrid.length, n = obstacleGrid[0].length;

        int[] f = new int[m];

        f[0] = obstacleGrid[0][0] == 0 ? 1 : 0;

        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                if (obstacleGrid[i][j] == 1) {
                    f[i] = 0;
                    continue;
                }
                if (i - 1 >= 0) {
                    f[i] += f[i - 1];
                }
            }
        }

        return f[m - 1];
        
    }
```
