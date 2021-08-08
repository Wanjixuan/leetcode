// 有点动态规划那味，这个和斐波那契数列一个套路，基本不用改什么

class Solution {
    public int tribonacci(int n) {

        if (n < 1) return 0;
        int[] ret = new int[n + 1];
        switch (n) {
            case 1:
                return 1;
            case 2:
                return 1;
        }
        ret[0] = 0;
        ret[1] = 1;
        ret[2] = 1;

        for (int i = 3; i <= n; i++) {
            ret[i] = ret[i - 1] + ret[i - 2] + ret[i - 3];
        }

        return ret[n];
    }
}
