class Solution {
    public int shortestPathLength(int[][] graph) {
       // BFS
        // 并不需要求出经过了哪些点，只是要知道遍历完以后最短的路径
        // 最短路劲的判定！！！
        int n = graph.length;
        // store node
        Queue<int[]> queue = new LinkedList<>();
        Map<Integer, INteger> map = new HashMap<>();
         
        // curNode、 through?、 dist:length
        for (int i = 0; i < n; i++) {
            queue.offer(new int[] {i, 1 << i, 0});
            map.put(i, {1 << i});
        }
        int ret = 0;
        while (!queue.isEmpty()) {
            int[] Per = queue.poll();
            int i = Per[0], mask = Per[1], dist = Per[2];
            // ????这步！！
            if (mask == (1 << n) - 1) {
                ret = dist;
                break;
            }

            // 遍历每个子数组了
            for (int Innum : graph[i]) {
                int newMask = mask | (1 << Innum);
                if (!map.get(Innum)[newMask]) {
                    // 没有在这轮里遍历过
                    queue.offer(new int[]{Innum, newMask, dist+1});

                    int tmp = map.get(Innum);
                    tmp[newMask] = true;

                    map.put(Innum, tmp);
    
                }
            }

        }
        return ret;
    }
}
