# [743. 网络延迟时间](https://leetcode-cn.com/problems/network-delay-time/)

思路：

Dijkstra算法：适用单源最短路径（有向、无向都行）**但是算法不能解决负权值问题。还是需要使用Bellman-Ford算法或者SPFA算法**

给定最后要到的点，然后再给个开始的点，先找离自己最近的点，然后移动位置到当前点去，看看我更新了点，堆其他点的距离会不会有影响，需要更新。以此循环上述操作



1、需要把两点之间的连通性以及权重输入到邻接矩阵或者邻接表中，亦或是前向星建图，graph

2、路径，那必然有一个存储路径的数组， distance

3、防止重复访问，我还需要一个标志数组，visit

4、这里涉及一个贪心的思想，每次都寻找最优的，这里实现可以通过

- 枚举
- 每次都最小，利用堆



## 邻接数组表示

```java
    boolean[] visit;
    // 之所以用INF / 2，因为后面会有与最大值得加法，会溢出
    int INF = Integer.MAX_VALUE / 2;
    public int networkDelayTime(int[][] times, int n, int k) {
        //DFS

        visit = new boolean[n];
        int[][] graph = buildGraph(times, n);
        int[] distance = new int[n];
        Arrays.fill(distance, INF);
        distance[k - 1] = 0;
        for (int i = 0; i < n; i++) {
            //确定初始点，另外先找到离当前点最近的作为下一个点
            int pos = -1;
            int minDTmp = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                // 下面这个语句前半句：遍历没有遍历过的
                // 后半句，用来找最小值
                if (!visit[j] && (pos == -1 || distance[j] < distance[pos])) {
                    pos = j;
                }
                // 找到离我最近的结点
            }
            visit[pos] = true;
            //更新距离，因为我现在确定了一个点了
            for (int j = 0; j < n; j++) {
                distance[j] = Math.min(distance[j], distance[pos] + graph[pos][j]);
            }
            
        }
        int ans = Arrays.stream(distance).max().getAsInt();
        return ans == INF ? -1 : ans;
    }

    public int[][] buildGraph(int[][] times, int n) {
        int[][] graph = new int[n][n];
        for (int[] per : graph) {
            Arrays.fill(per, INF);
        }

        for (int i = 0; i < times.length; i++) {
            int start = times[i][0] - 1, end = times[i][1] - 1, weight = times[i][2];
            graph[start][end] = weight; 
        }
        return graph;

    }
```



## 堆优化：

但是由于这个图比较稠密，时间上会花的久一点，所以枚举会快于堆的写法

```java
    boolean[] visit;
    // 之所以用INF / 2，因为后面会有与最大值得加法，会溢出
    int INF = Integer.MAX_VALUE / 2;
    public int networkDelayTime(int[][] times, int n, int k) {
        //DFS

        visit = new boolean[n];
        int[][] graph = buildGraph(times, n);
        int[] distance = new int[n];
        Arrays.fill(distance, INF);
        distance[k - 1] = 0;
        // 将距离作为排序的决断
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        // [下一个结点，距离]
        queue.offer(new int[]{k - 1, 0}); 
        while (!queue.isEmpty()) {
            
            int[] next = queue.poll();
            int nextNode = next[0];
            // 找离我最近的点
            if (visit[nextNode]) continue;// 如果访问过直接跳过
            visit[nextNode] = true;

            //更新距离，因为我现在确定了一个点了
            for (int j = 0; j < n; j++) {
                if (distance[j] > distance[nextNode] + graph[nextNode][j]) {
                    distance[j] = distance[nextNode] + graph[nextNode][j];
                    queue.offer(new int[]{j,distance[j]});
                }
            }
            
        }
        int ans = Arrays.stream(distance).max().getAsInt();
        return ans == INF ? -1 : ans;

    }

    public int[][] buildGraph(int[][] times, int n) {
        int[][] graph = new int[n][n];
        for (int[] per : graph) {
            Arrays.fill(per, INF);
        }

        for (int i = 0; i < times.length; i++) {
            int start = times[i][0] - 1, end = times[i][1] - 1, weight = times[i][2];
            graph[start][end] = weight; 
        }
        return graph;

    }
```





## 邻接表方法

这个方法用来求最短路径不是特别好，最好搭配堆优化，因为每次更新其他位置的距离的时候，不好弄



## 邻接表堆优化

- 用Map映射存储邻接表

```java
    boolean[] visit;
    // 之所以用INF / 2，因为后面会有与最大值得加法，会溢出
    int INF = Integer.MAX_VALUE / 2;
    public int networkDelayTime(int[][] times, int n, int m) {
        
        // list代表的是。我相邻的点有哪些，但是具体我和他们之间的位置，还需要一个数来存
        // 因此放在int[]数组中，每个int[]相当于一个相邻的点
        // 也就是说 map 实际上存的是边
        Map<Integer, List<int[]>> map = new HashMap<>();

        //因为是有向图，所以可以这样创建
        for (int[] arr : times) {
            // 把1-n，映射成0-（n-1）
            int a = arr[0] - 1, b = arr[1] - 1;
            map.computeIfAbsent(a, k -> new ArrayList<>()).add(new int[]{b, arr[2]});
        }
        int[] distance = new int[n];
        Arrays.fill(distance, INF);
        //把起始点的距离设置为0
        distance[m - 1] = 0;
        visit = new boolean[n];
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> (o1[1]-o2[1]));
        // 0：结点位置，1：距离k-1
        pq.offer(new int[] {m - 1, 0});

        while (!pq.isEmpty()) {
            int[] po = pq.poll();
            
            int cur = po[0];
            if (visit[cur]) continue;
            visit[cur] = true;
            // 这个时候已经确定是最小值了
            List<int[]> list = map.getOrDefault(cur, Collections.emptyList());
            for (int[] arr : list) {
                int next = arr[0];
                if (visit[next]) continue;

                distance[next] = Math.min(distance[next], distance[cur]+arr[1]);
                pq.offer(new int[] {next, distance[next]});
            }
        }
        int ans = Arrays.stream(distance).max().getAsInt();
        return ans == INF ? -1 : ans;
    }
```

- 用`List<int[]>`来接收，第一个`int[]`存的是当前的点，后序存的是相邻的结点以及距离组成的多`int[]`

```java
    boolean[] visit;
    // 之所以用INF / 2，因为后面会有与最大值得加法，会溢出
    int INF = Integer.MAX_VALUE / 2;
    public int networkDelayTime(int[][] times, int n, int k) {
        
         final int INF = Integer.MAX_VALUE / 2;
        List<int[]>[] g = new List[n];
        // 先把各结点存进去，
        for (int i = 0; i < n; ++i) {
            g[i] = new ArrayList<int[]>();
        }
        // 在各个结点后面讲相邻的点以及距离存进去。
        for (int[] t : times) {
            int x = t[0] - 1, y = t[1] - 1;
            g[x].add(new int[]{y, t[2]});
        }

        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[k - 1] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        // 先将初始值传进去
        pq.offer(new int[]{0, k - 1});
        while (!pq.isEmpty()) {
            int[] p = pq.poll();
            // 假设当前弹出的数值不够更新，也就是距离比压缩进去的还小，就干脆下一个。
            int time = p[0], x = p[1];
            if (dist[x] < time) {
                continue;
            }
            // 此时得到最小的结点以后，开始遍历更新后面的距离。
            for (int[] e : g[x]) {
                int y = e[0], d = dist[x] + e[1];
                // 如果比distance数组里存储的路径还要小，更新数据
                if (d < dist[y]) {
                    dist[y] = d;
                    pq.offer(new int[]{d, y});
                }
            }
        }

        int ans = Arrays.stream(dist).max().getAsInt();
        return ans == INF ? -1 : ans;

    }
```

## Floyd 算法

![算法思路](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/743-1.png)

### 模板
![算法模板](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/743-2.png)


### 代码
```java

class Solution {
    // 之所以用INF / 2，因为后面会有与最大值得加法，会溢出
    int INF = Integer.MAX_VALUE / 2;
    int[][] D = new int[110][110];
    int n, m;
    public int networkDelayTime(int[][] times, int n1, int m1) {
        
        // D = new int[n][n];
        n = n1;
        m = m1;
        // 初始化
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                D[i][j] = D[j][i] = i == j ? 0 : INF;
     
            }
        }

        // 存图
        for (int[] arr : times) {
            int start = arr[0] - 1, end = arr[1] - 1;
            D[start][end] = arr[2];
        }
        floyd();

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, D[m - 1][i]);
        }

        return ans == INF ? -1 : ans;


    }

    public void floyd() {
        
        // 慢慢加入集合中点的个数
        for (int k = 0; k < n; k++) {
            // 在给定 k 个点的情况下，求出最短的距离！
            // 如果有限的k，不能使距离从INF改变，那么还需要扩展，说明这堆k里，没有路径过去
            // 如果能给的k都给完了，还没有从INF改变，那么说明就没有路径
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    D[i][j] = Math.min(D[i][j], D[i][k] + D[k][j]);
                }
            }
        }
    }

}
```
