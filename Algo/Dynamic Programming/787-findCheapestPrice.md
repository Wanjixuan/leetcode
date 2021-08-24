# [787. K 站中转内最便宜的航班](https://leetcode-cn.com/problems/cheapest-flights-within-k-stops/)

## 思路
首先最便宜的航班，并且给出了源点src和终点dst，第一想法就是最短路径算法，把边的权重变为价格

首先思考`Djikstra`算法 <br/>
1、给定源点和终点，求最便宜，但是中转不能超过K <br/>
2、说明肯定有多条能够到达终点的，但不一定是最短的，我对每一条我需要一个数据结构来接收<br/>
会有以下问题：<br/>
- 首先我不能超过k个中转，假设如果遍历到某个点的时候超过了，那我该从哪个点开始改变呢？？所以`Djikstra`可能不太行<br/>


换个思路：我经过K次中转到达某个城市，并且这时候，刚好`dst`这个城市就在这个集合里<br/>
参考硬币问题，假设第k个时候刚好我就到了`dst`这个点，那么反着推，我是不是必须得是第k-1个中转就必须是最优的，不然凭什么让我相信最后一个就是最优的！<br/>
这就是一个动态规划的转移方程了:在第k-1次中转后，我取得了这时候的最优解，然后我需要比较（原始第k-1次的时候价格，经过第k-2那个点再中转过来的价格），看哪个更小 <br/>

于是状态方程就出来了<br/>
![状态转移方程](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/787.png)

初始化的时候需要注意：我从源点出发，中转了k次，转移是几次呢？如下图。
![转移示例](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/787-1.png)

当然还有个小细节，如果两个城市之间没有航线，应该是无穷大，如果 i 是出发城市 `src`，那么花费为 0；否则 `f[0][i]` 不是一个合法的状态，由于在状态转移方程中我们需要求出的是最小值，因此可以将不合法的状态置为极大值 `∞`。
![小细节](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/787-2.png)



## 代码
```java
    int INF = Integer.MAX_VALUE / 2;
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // 给定源点和终点，求最便宜，但是不能超过K，
        // 说明肯定有多条能够到达终点的，但不一定是最短的，我对每一条我需要一个数据结构来接收
        // 麻烦的点在于 如果我顺着来，也就是选择源点到终点，如果不行，我又从哪条路线开始截止呢？
        // 所以其实这是一个动态规划的问题
        // k站中转，就是说，中转了k次，就是说中间有k个城市，加上源点和终点经过了K+2个城市，中间是有k+1次转移的
        // 
        // 数组【第i次转移，第i次跳转到哪个城市】，值表示由第0次的城市，到这个城市的最便宜票价
        int[][] f = new int[k+2][n];
        for (int[] arr : f) {
            Arrays.fill(arr, INF);
        }
        // i=0，就是刚开始的时候，
        f[0][src] = 0;

        // 我需要找出 我飞到这个城市，是不是会比这个城市已经到过这个城市更便宜

        for (int i = 1; i < k + 2; i++) {
            for (int[] arr : flights) {
                //应该找价格矩阵
                int start = arr[0], end = arr[1], price = arr[2];
                // 当第i次到end这个城市的最短路径，进行更新，
                // 看下我在第i - 1次基础上，通过start，再转到这个点会不会更小
                f[i][end] = Math.min(f[i][end], f[i - 1][start]  + price);
            }
        }

        int ans = INF;
        for (int i = 1; i < k+2; i++) {
            ans = Math.min(ans, f[i][dst]);
        }

        return ans == INF ? -1 : ans;
    }
```


### 优化
我们其实只关心，是否在第k次转移的时候，能到dst，<br/>
在状态转移中，我们需要使用二重循环枚举 `t` 和 `i`，随后枚举所有满足 `(j, i) ∈flights` 的 `j`，这样做的劣势在于没有很好地利用数组 `flights`，为了保证时间复杂度较优，需要将 `flights` 中的所有航班存储在一个新的邻接表中。一种可行的解决方法是，我们只需要使用一重循环枚举 tt，随后枚举 \textit{flights}flights 中的每一个航班 `(j,i,cost)`，并用 `f[t-1][j]` + `f[t−1][j]+cost` 更新 `f[t][i]`，这样就免去了邻接表的使用。<br/>

注意到 `f[t][i]` 只会从 `f[t−1][..] `转移而来，因此我们也可以使用两个长度为 `n` 的一维数组进行状态转移，减少空间的使用。<br/>

```java
class Solution {
    int INF = Integer.MAX_VALUE / 2;
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // 每次都是只用了两行，一个转移只涉及两座城市，分别用两个数组代表这两次时候两个城市

        int[] f = new int[n];

        Arrays.fill(f, INF);
        f[src] = 0;
        // 假设没有！
        int ans = INF;
        for (int i = 1; i < k + 2; i++) {
            // 用来存本次中转后的价格数组,后面会利用f数组替代
            // f是上次，tmp是转移到的本次
            // f:第i-1次， tmp是 第i次。
            int[] tmp = new int[n];
            // 同样需要防止出现特殊值。
            Arrays.fill(tmp, INF);
            for (int[] arr : flights) {
                int start = arr[0], end = arr[1], price = arr[2];
                // 本次，和（上次+通过这个航路转过来）：比较取最小值
                tmp[end] = Math.min(tmp[end], f[start]+price);
            }

            f = tmp;
            ans = Math.min(ans, f[dst]);           
        }

        return ans == INF ? -1 : ans;
    }
}
```

## 还有几种最短路径的算法！后续补上，三叶大佬的！
