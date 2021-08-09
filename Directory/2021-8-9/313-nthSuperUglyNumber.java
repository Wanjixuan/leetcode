// 思路：

// 暴力法
class Solution {
    public int nthSuperUglyNumber(int n, int[] primes) {
        // 暴力法，从头判断过去！
        // 先判断某个数字是丑数

        int count = 0;
        int cur = 1;
        while (count < n) {
            if (isUgly(primes, cur)) {
                count++;

            }
            cur++;
        }

        return cur - 1;
    }
    private static boolean isUgly(int[] primes, int cur) {
        for (int prime : primes) {

            while (cur%prime == 0) {
                cur /= prime;
            }
        }

        return (cur == 1) ? true : false;
    }
}

// 类似备忘录的最小堆法
class Solution {
    public int nthSuperUglyNumber(int n, int[] primes) {
        // min deap
        PriorityQueue<Long> pq = new PriorityQueue<>();
        // 防止出现重复的丑数
        HashSet<Long> set = new HashSet<>();

        // 从最小的开始取，每次取出来以后，再乘prime数组里面的
        pq.offer(1l);
        set.add(1l);
        long ugly = 0;

        for (int i = 0; i < n; i++) {
            long tmp = pq.poll();
            ugly = tmp;
            for (int j = 0; j < primes.length; j++) {
                long tmpX = tmp * primes[j];
                if (set.add(tmpX)) {
                    pq.offer(tmpX);
                }
            }


        }
        return (int)(ugly);
    }

}
