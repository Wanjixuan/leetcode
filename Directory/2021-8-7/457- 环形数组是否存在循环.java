// https://leetcode-cn.com/problems/circular-array-loop/

//思路：
//首先环的话，要处理好下标，考虑到有负数以及越界的情况.
// 根据题意，我们可以从每个下标 ii 进行出发检查，如果以某个下标 ii 为出发点发现了「循环」，返回 True，否则返回 False。

// 唯一需要注意的细节是，当我们处理到的下标为 curcur，计算下一个跳转点 next = cur + nums[cur]next=cur+nums[cur] 时，对于越过数组的情况进行处理：

// 如果 nextnext 为负数：在 nextnext 的基础上增加 n * \left \lceil next / n \right \rceiln∗⌈next/n⌉，将其映射回正值；

// 如果 nextnext 为正数：将 nextnext 模数组长度 nn，确保不会越界。

// 整理一下，我们可以统一写成 next = ((cur + nums[cur]) % n + n ) % n。

// 在 check 内部，当以下任一条件出现，则可以结束检查（令 kk 为记录过程中扫描过的下标数量）：

// 如果在检查过程中，找到了与起点相同的下标，且 k > 1k>1，说明存在符合条件的「循环」，返回 True；

// 如果检查过程中扫描的数量 kk 超过了数组长度 nn，那么根据「鸽笼原理」，必然有数被重复处理了，同时条件一并不符合，因此再处理下去，也不会到达与起点相同的下标，返回 False；

// 处理过程中发现不全是正数或者负数，返回 False。

// 作者：AC_OIer
// 链接：https://leetcode-cn.com/problems/circular-array-loop/solution/gong-shui-san-xie-yi-ti-shuang-jie-mo-ni-ag05/
// 来源：力扣（LeetCode）

class Solution {
    public boolean circularArrayLoop(int[] nums) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (dfs(i, nums)) {
                return true;
            }
        }

        return false;
    }

    private static boolean dfs(int i, int[] nums) {

        int n = nums.length;
        int k = 1;
        int cur = i;
        boolean flag = nums[i] > 0;
        while (true) {
            // 如果一直没有回到原来的位置，可能在某个位置重复了
            //  [-1,-2,-3,-4,-5]
            if (k > n) return false;
            // 还得考虑next为负的情况，根据(nums[cur] + cur) + n就可以转正，和正数部分结合可以得到下面的
            int nextIndex = ((nums[cur] + cur) % n + n) % n;
            if (flag && nums[nextIndex] < 0) return false;
            if (!flag && nums[nextIndex] > 0) return false;
            if (nextIndex == i) return k > 1;
            cur = nextIndex;
            k++;
        }
    }
}
