# [797. 所有可能的路径](https://leetcode-cn.com/problems/all-paths-from-source-to-target/)

## 思路
题目给定信息：
- 没有环：意味着不会有对着一个点遍历出现重复遍历的情况
- 每个对应 `i`存的是自己的next结点。
- 特别关键一点：最后一个结点没有next结点，否则，容易形成环

首先，如果想访问到最后的`end`，对每一个经过的结点start，把他的所有`next`都走一遍，这样如果遍历到了`end`说明这个路径是可行的<br/>
这便是深度优先算法<br/>

几个问题:
- 我怎么知道我遍历到了`end`：当前结点准备跳下一个，我的next数组里给定了我的下一个目标，如果目标等于`end`的话。说明找到了
- 找到`end`后我该干嘛？：当然是先返回上一个结点（就是跳到`end`结点的那个），再从他的next数组的其他目标进行遍历。前面的路径还是能用，但是注意，需要把当前结点删掉！！！
- 当前的时候没有实现`start==end`怎么办？：继续深度遍历我这个`start`中next数组，看看我的后续能不能访问到这个`end。
- 如果遍历完了这个start的next数组，还是没有，要返回start的上一层，同样，需要把当前path的最后一个结点去掉。


## 代码
```java
class Solution {
    // 因为没有环，就不需要 visit辅助数组了
    List<List<Integer>> res = new ArrayList<>();
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        
        List<Integer> list = new ArrayList<>();
        dfs(graph, 0, list, graph.length - 1);
        return res;
    }

    public void dfs(int[][] graph, int start, List<Integer> path, int end) {
        // 深度优先就是，求start到end之间的点
        // 挑每一号结点，他经过的结点都进行深度遍历，停止了就返回start那个数组
        // 先把s放进来，
        path.add(start);

        int n = graph.length;
        // 终止条件:   开始和结束一样
        if (start == end) {
            //到了如果包括了最后一个点，那么就放入我们要返回的res集合俩民
            // 如果不包括最后一个点？
            res.add(new ArrayList<Integer>(path));

            // 我现在要返回start那个结点，继续那个结点的其他next遍历
            // 之前的路径还能用，但是现在的path加了end，就不能用了，先去掉最后一个加进去的
            path.remove(path.size() - 1);
            return;
        }
        //如果没有终止
        // 每个结点是说，我里面可以到这么多条路，那么我相邻的每个结点都找一下去end的路
        for (int node : graph[start]) {
            dfs(graph, node, path, end);
        }

        // 这个意思就是说，遍历完了start结点的所有next结点，该返回start的上一个结点了
        // 首先，同样要先把start给去了
        path.remove(path.size() - 1);
    }
}

```
