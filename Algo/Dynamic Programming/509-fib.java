// 解题思路
// 思路来自东哥的一篇博客：https://labuladong.gitee.io/algo/3/23/57/

// ### 暴力法
// 就是递归，需要注意就是返回条件，当然这样会使得内存炸了。
// 原因是重复计算，比如 20 需要计算 19 18， 求 19 的时候又要计算一遍 18，往后面，是指数级增长。
// 优化思路就是：
//     - 记录下每个计算过的数，每次需要的时候查一下，如果计算过直接返回


class Solution {
    public int fib(int n) {

        // 暴力法
        int ret = dfs(n);
        return ret;
    }

    public int dfs(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
 
        return dfs(n - 1) + dfs(n - 2);
    }
}


// ### 备忘录
// 在暴力法的基础上加入一个备忘录，减少重复计算的过程

class Solution {
    public int fib(int n) {

        // 备忘录
        if (n < 1) return 0;
        int[] memo = new int[n + 1];
        int ret = dfs(n, memo);
        return ret;
    }

    public int dfs(int n, int[] memo) {
        if (n == 1 || n == 2) {

            return 1;
        }
        if (memo[n] != 0) {
            // have done 
            return memo[n];
        } 
        // no? calculate
        memo[n] = dfs(n - 1, memo) + dfs(n - 2, memo);
        return memo[n];
        

    }
}


// ### 动态规划
// 从最底层开始计算，先确定初始条件、然后是转移方程，具体看东哥的讲解，很详细了。



class Solution {
    public int fib(int n) {

        // 动态规划
        // 自低向上
        if (n < 1) return 0;
      
        int[] ret = new int[n+1];

        // 相当于初始条件
        ret[0] = 0;
        ret[1] = 1;

        for (int i = 2; i <= n; i++) {
            // 转移方程
            ret[i] = ret[i - 1] + ret[i - 2];
        }

        return ret[n];
    }

}


// 可以看出除了n=1,n=2特殊，其他的都是只与前两个状态相关
// 因此可以只记录前两个状态


class Solution {
    public int fib(int n) {

        // 动态规划
        // 自顶向下
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;
        // 相当于初始条件
        // 现在的状态只与前两个状态相关，不需要数组存储
        int pre = 1;  // i = 1
        int cur = 1;  // i = 2;

        for (int i = 3; i <= n; i++) {
            int sum = pre + cur;
            pre = cur;
            cur = sum;
        }

        return cur;
    }

}
