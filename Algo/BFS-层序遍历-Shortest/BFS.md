## 思路
把当前点最近的所有点全部放进队列里，然后执行遍历，同时给标志位，防止后续再遍历到这个点
接着某个点出队后，把它周围的点再次全部放进来

BFS 更像是一圈一圈的往外遍历，放在二叉树就是层序遍历的基本框架

## 模板



```java
// 计算从起点 start 到终点 target 的最近距离
public int BFS(Node start, Node target) {
    Queue<Node> q = new LinkedList<>(); // 核心数据结构
    Set<Node> visited = new HashSet<>(); // 避免走回头路

    q.offer(start); // 将起点加入队列
    visited.add(start);
    int step = 0; // 记录扩散的步数

    while (!q.isEmpty()) {
        int sz = q.size();
        /* 将当前队列中的所有节点向四周扩散 */
        for (int i = 0; i < sz; i++) {
            Node cur = q.poll();
            /* 划重点：这里判断是否到达终点 */
            if (cur is target)
                return step;
            /* 将 cur 的相邻节点加入队列 */
            for (Node x : cur.adj())
                if (x not in visited) {
                    q.offer(x);
                    visited.add(x);
                }
        }
        /* 划重点：更新步数在这里 */
        step++;
    }
}

```

### 题目
[102. 二叉树的层序遍历](https://leetcode-cn.com/problems/binary-tree-level-order-traversal/)

![102](https://user-images.githubusercontent.com/32134770/129651855-eb68066b-e42c-4157-b3e4-ff7e49439bd2.png)

```java
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        if (root == null) {
            return ret;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<Integer>();
            int currentLevelSize = queue.size();
            for (int i = 1; i <= currentLevelSize; ++i) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            ret.add(level);
        }
        
        return ret;
    }

```

