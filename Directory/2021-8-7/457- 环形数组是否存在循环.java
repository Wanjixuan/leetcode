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

### 解题思路
双指针分有两份代码：第一份是根据官方题解思路写的，第二个几乎就是官方题解，改动不大
第一份之所以那么慢：
    - 是因为访问了好些重复得点，所以需要增加一个访问数组来表示访问情况 boolean[]
    - 另一种方法，也就是题解的方法，如果访问过，也就说明这个点经过这个点的环不满足条件，直接结合到是否异号的那里面去，把这个点置为0；

1、首先明确返回 false 的条件：
    - 没有环：遍历过后就没有的话
    - 有环但是长度只有1，也就是一直在某个数循环，可能有一下两种情况：
        - nums[i] == i 或者 下一个遍历的点又是自己
    - 同一个环中有异号的情况：快慢指针代表的数相乘 < 0  快快慢指针代表的数相乘 < 0
2、如何遍历一个环：
    - 下一个如果是正数，那么防止下标溢出：(i + nums[i]) % n
    - 下一个如果是负数，转正：(i + nums[i]) % n + n
    - 综合一下：((i + nums[i]) % n + n) % n

### 代码


class Solution {
    public boolean circularArrayLoop(int[] nums) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            // 每个作为起点都来一个快慢指针
            // dfs返回的是一个下标
            int slow = i, fast = dfs(i, nums);
            // 同向并且排除自己循环,快慢指针代表的数符号相同，乘起来同号
            while (nums[i] != n && nums[slow]*nums[fast] > 0 && nums[slow] * nums[dfs(fast, nums)] > 0) {
                // 同号已经满足了
                // k > 1  还差一个条件
                
                // 接下来就是深入遍历
                if (slow != fast) {
                    slow = dfs(slow, nums);
                    fast = dfs(dfs(fast, nums), nums);
                } else {
                    if (slow != dfs(slow, nums)) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
        }
        return false;
    }
     private static int dfs(int i, int[] nums) {

        int n = nums.length;

        return ((i + nums[i]) % n + n) % n;

    }

}

class Solution {
    public boolean circularArrayLoop(int[] nums) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            // 每个作为起点都来一个快慢指针
            // dfs返回的是一个下标
            int slow = i, fast = dfs(i, nums);
            // 同向并且排除自己循环,快慢指针代表的数符号相同，乘起来同号
            while (nums[i] != n && nums[slow]*nums[fast] > 0 && nums[slow] * nums[dfs(fast, nums)] > 0) {
                // 同号已经满足了
                // k > 1  还差一个条件
                
                // 接下来就是深入遍历
                if (slow != fast) {
                    slow = dfs(slow, nums);
                    fast = dfs(dfs(fast, nums), nums);
                } else {
                    if (slow != dfs(slow, nums)) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            // 就是置零了，让这个不能满足外层的那个while 条件，表示我遍历过，再遇到环中有这个数的，直接pass
            int add = i;
            while (nums[add] * nums[dfs(add, nums)] > 0) {
                int tmp = add;
                add = dfs(add, nums);
                nums[tmp] = 0;
            }
        }
        return false;
    }
     private static int dfs(int i, int[] nums) {

        int n = nums.length;

        return ((i + nums[i]) % n + n) % n;

    }

}


### 模拟解法：
惭愧，这个没写出来，有点思路但是很乱，最后参考了三叶大佬的，并且三叶大佬的图算法没有看懂
思路：
与上面的思路相仿。
- 是否同号：用标志位表示，很棒！
- k > 1：每次遍历的时候+1
- 避免重复：利用抽屉原理
### 代码：

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
        // 
        boolean flag = nums[i] > 0;
        while (true) {
            // 抽屉原理，在这里就是不管怎么遍历，最后环的长度都不应该超过我原数组的长度
            if (k > n) return false;
            int nextIndex = ((nums[cur] + cur) % n + n) % n;
            // 下面两个 if 就是判断是否同号
            if (flag && nums[nextIndex] < 0) return false;
            if (!flag && nums[nextIndex] > 0) return false;
            // 有相遇的情况了！
            if (nextIndex == i) return k > 1;
            cur = nextIndex;
            k++;
        }
    }
}

