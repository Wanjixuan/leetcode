[136. 只出现一次的数字](https://leetcode-cn.com/problems/single-number/)

## 思路
第一想法用HashMap,来映射每个数字出现的次数，但是时间空间复杂度不够满足题意，意味着能继续优化 <br/>

如果数组有序，也可以得到遍历得到只出现一次的数，通过快速排序的话空间复杂度为O(mlogm) <br/>

可优化点：
- 遍历一遍有解决方法，但是空间复杂度不够
- 题目提示：
-   空间复杂度为O(1),意味着可能是遍历过去的时候，元素之间的运算。
-   又有提示有重复的都是2个
-   这里补充个知识点，异或这种位运算，具有异或运算满足交换律，a^b^a=a^a^b=b,因此ans相当于nums[0]^nums[1]^nums[2]^nums[3]^nums[4]..... 然后再根据交换律把相等的合并到一块儿进行异或（结果为0），然后再与只出现过一次的元素进行异或，这样最后的结果就是，只出现过一次的元素（0^任意值=任意值）


## 代码

### HashMap版
```java
    public int singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
       for (int i : map.keySet()) {
           if (map.get(i) == 1) {
               return i;
           }
       }
        return 0;
    }
```

## 位运算（异或）

```java
    public int singleNumber(int[] nums) {
        int ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            ans = ans^nums[i];
        }
        return ans;
    }
```



