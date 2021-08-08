/**
思想是前缀和，关键需要看出，如果是正常的话，对称 意味着一直到最后一个之前，[ 的数量都是大于 ] 的。
于是，可从前向后遍历，并统计左右括号之差，记为 diffdiff。

在遍历过程中，如果在位置 ii 处，diffdiff 小于零了，那么此位置必是]。于是需从 ii 之后的位置，挑选一个[ 并交换。

因为我们要是所有位置的 diffdiff 都不为负，所以在挑选[时从后向前挑选即可。


至于为什么这样是最小？？
*/


class Solution {
    public int minSwaps(String s) {
        int n = s.length();
        int diff = 0;
        int ret = 0;
        char[] chars = s.toCharArray();

        for (int i = 0, j = n - 1; i < n; i++) {
            if (chars[i] == '[') {
                diff++;
            } else {
                diff--;
            }

            while (diff < 0) {
                // 这个时候必定是 ] , 需要从后面拿一个 [
                while (chars[j] == ']') j--;

                // found, exchange
                chars[i] = '[';
                chars[j] = ']';
                // 为什么 +2？ 
                // 因为换过以后，后序遍历的时候会遍历到换过去的 ] ，又多-1
                diff += 2;
                ret++;
            }
        }
        return ret;

    }
}
