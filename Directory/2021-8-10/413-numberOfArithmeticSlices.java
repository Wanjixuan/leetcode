// ### 解题思路
// 因为等差数列是连续的，并且分析规律，
// - 3-1
// - 4-3
// - 5-6
// - 6-10
// 等差数组长度为 n, 则有 (n-1) * (n - 2) / 2个子等差数组。
// 所以只需要统计原数组中，每个连续等差的长度就可以了（一定要是最长），比如  1,2,3,5,6,7  有两个 长度为 3 的等差子数组
// 需要注意的一点，差分的话，计算出来的长度与实际长度差 1.因为差分算的是间隙。
// 所以，判定条件也是定在了 count >= 2, 在`(n - 2) * (n - 1) / 2 `基础上，令 `n = n + 1` 带入，就是 `(n - 1) * (n) / 2`

### 代码


class Solution {
    public int numberOfArithmeticSlices(int[] nums) {
        // 至少三个元素
        // 公式为 (n - 1) * (n - 2) / 2
        if (nums.length < 3) {
            return 0;

        }
        int n = nums.length;
        List<Integer> list = new ArrayList<>();

        int sum = 0;
        int lastDiff = nums[1] - nums[0];
        int count = 1;
        for (int i = 2; i < n; i++) {
            int diff = nums[i] - nums[i - 1];
            if (diff == lastDiff) {
                count++;
            } else {
                // 之所以是选择2，是因为计数的
                if (count >= 2) list.add(count);
                count = 1;
            }

            lastDiff = diff;
        }
        // 这一步是防止最后那段是等差数列，但是遍历完的时候已经退出来了。
        if (count >= 2) list.add(count);
        for (int num : list) {
            // 因为3个数，会有两个间隙，所以，在(n - 2) * (n - 1) / 2 基础上，令 n = n + 1 带入
            sum += (num) * (num - 1) / 2;
        }

        return sum;
        
    }


}
