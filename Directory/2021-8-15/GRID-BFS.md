

如下思路比较好接受。
```java

class Solution {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    int n;
    int[][] grid;

    public int maxDistance(int[][] grid) {
        this.n = grid.length;
        this.grid = grid;
        int ans = -1;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 0) {
                    ans = Math.max(ans, findNearestLand(i, j));
                }
            }
        }
        return ans;
    }

    public int findNearestLand(int x, int y) {
        boolean[][] vis = new boolean[n][n];
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[]{x, y, 0});
        vis[x][y] = true;
        while (!queue.isEmpty()) {
            int[] f = queue.poll();
            for (int i = 0; i < 4; ++i) {
                int nx = f[0] + dx[i], ny = f[1] + dy[i];
                if (!(nx >= 0 && nx < n && ny >= 0 && ny < n)) {
                    continue;
                }
                if (!vis[nx][ny]) {
                    queue.offer(new int[]{nx, ny, f[2] + 1});
                    vis[nx][ny] = true;
                    if (grid[nx][ny] == 1) {
                        return f[2] + 1;
                    }
                }
            }
        }
        return -1;
    }
}

作者：LeetCode-Solution
链接：https://leetcode-cn.com/problems/as-far-from-land-as-possible/solution/di-tu-fen-xi-by-leetcode-solution/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```
