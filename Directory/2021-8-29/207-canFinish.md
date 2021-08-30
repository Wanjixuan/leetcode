# [207. 课程表](https://leetcode-cn.com/problems/course-schedule/)



## 思路：

这是一个典型的`AOV`网络：真实活动表现在顶点上， 有向边表示活动发生的先后顺序



能不能上完关键是看，a上完才能上b，b上完才能上c，c上完才能上a。类似这种出现了课程预修之间的环

可以用图来解决

- 1、第一种：判断某个课程一直深入下去会不会出现环
- 2、第二种：总有课程的入度不为0，而且是无法减少到0的（拓扑排序）



## DFS：

课程是从 0 到 n-1 编号的，如果任意从某个顶点进去上课，**在那一轮不会对本轮上的课程有再次遍历**，就说明无环！

为什么说 **在那一轮呢？** 因为本题并不是访问过就不能再访问！如果有一门课`a`需要先学完多门课，这种从多门课进行访问时候都会经过这个点`a`！这也是允许的！因此不能通过这个来判断！必须是本轮课程时候再遍历！

并且遍历过后，需要把最初始那个点置为轮数内的未访问，方便后续的其他课程来访问他！



如果`AOV`网络，有合理的拓扑序，那么一定是**有向并且无环的图**

- 合理：`V`在`V`开始之前就应该结束



**结束条件：**

我曾经在某一轮遍历过这个点，说明后续之前肯定是遍历过了，不需要再访问了！不然就是重复访问了！

**有环条件：**

如果我在**本轮的遍历过程中**重复遍历到某个点，一定是本轮！



```java
class Solution {
    boolean[] visit;
    boolean[] onPath;
    boolean hasCycle = false;
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new LinkedList[numCourses];
        visit = new boolean[numCourses];
        onPath = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            // 先新建一些节点！
            graph[i] = new LinkedList<>();
        }
        // 把后续的课程放入之前的结点内
        for (int[] arr : prerequisites) {
            int start = arr[1], end = arr[0];
            graph[start].add(end);
        }
        // 建好图了，可以遍历，如果这些课程中有环，也就是图中有环，那么我就无法把这些课全部上完
        // DFS
        for (int i = 0; i < numCourses; i++) {
            dfs(graph, i);
        }
        return !hasCycle;
    }

    public void dfs(List<Integer>[] graph, int nowCor) {
        // if (visit[nowCor]) {
        // 标志位，如果这个节点我访问过，但是现在又来访问，也有可能是某门课需要好几门课来
        // 所以经过过，不代表就出现了环
        //     return;
        // }
        // 出现环，应该是说我在最初的那个节点下的深度遍历中，重复访问到了某个结点！
        // 如果我把某个结点下的后续结点都遍历了，但是还是没有发现有环，说明这个结点后续有环就不可能
        if (onPath[nowCor]) hasCycle = true;

        // 终止条件！
        // 经过上面的判断
        // 说明我在本轮遍历第一次遇到这个点，之前以这个结点遍历过，也就不需要我再去遍历了！所以直接返回！
        // 我只是判断是否有环！不是要不每个路径给输出，即使输出，只要把这个结点的后续，接到之前的遍历上！
        if (visit[nowCor]) return;
            
        visit[nowCor] = true;
        onPath[nowCor] = true;
        for (int next : graph[nowCor]) {
            dfs(graph, next);
        }
        // 如果这个结点能够最后走到这里，说明以这个结点的后续遍历是不存在环的
        onPath[nowCor] = false;
    }

}
```







## 拓扑排序（BFS）：



拓扑排序主要是建好图后，把入度未0的点先放进队列，有点类似多叉树的BFS。

如何把某个结点算丢弃呢？只需要把后续结点的入度减1就可以了！

这时候如果后续结点入度减完1，等于0，再把它压入队列中！

每个结点被队列弹出，说明我学完了这个课程，可以到后续去！

最后的话所有结点的入度都应该是0，否则就是有环！

有环：

- 第一种：最后入度数组是否都是0
- 第二种：因为进入队列的都是入度未0的，也就意味着被队列弹出的时候，这些课程都被顺利学完了，如果最后课程数不为0，说明还有课程我没学到



```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 建立一个图，方便遍历
        List<Integer>[] graph = new LinkedList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new LinkedList<>();
        }
        //利用入度求！
        // 先建立入度数组
        int[] Indegree = new int[numCourses];
        for (int[] arr : prerequisites) {
            int from = arr[1], to = arr[0];
            Indegree[to]++;
            graph[from].add(to);
        }
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            // 先把入度为0 的 点 放进队列
            if (Indegree[i] == 0) q.offer(i);
        }
        
        // 开始调整入度！也就是 BFS
        while (!q.isEmpty()) {
            // 弹出的是某个课程，因此课程数--
            int tmp = q.poll();
            numCourses--;

            for (int next : graph[tmp]) {
                // 进行 BFS遍历
                if (--Indegree[next] == 0) q.offer(next);
            }
        }
        // 上不完的条件：最后了，他的入度不为0
        // 或者 课程数还有！
        return numCourses == 0;
    }
}
```

