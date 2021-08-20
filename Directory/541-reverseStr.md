# 题目
[541. 反转字符串 II](https://leetcode-cn.com/problems/reverse-string-ii/)

## 思路
下列三种情况：
- 剩余大于 2k，反转前k个
- 剩余小于2k，但大于k，反转前k个
- 剩余不足k，剩余的全部反转
- 如图：

![情况划分](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/541-1.png)

总结：<br/>
我只需要关心，剩下的够不够k个就可以，不够k个直接到n都可以反转 <br/>
转换成代码就是下面这个表达式: 关心翻转的部分是 i + k, 还是一直到n都反转 <br/>
`reverse(arr, i, Math.min(i + k, n) - 1)`


## 代码

```java
    public String reverseStr(String s, int k) {
        int n = s.length();
        char[] arr = s.toCharArray();

        for (int i = 0; i < n;) {
            // 这个语句可以替换后面的，但是需要补上 i += 2*k 这个条件
            // reverse(arr, i, Math.min(i + k, n) - 1);
            
            // 这句话容易出错，并且十分多余
            // if (i + 2*k < n) {
            //     // 剩余 2k以上
            //     reverse(arr, i, i + k - 1);
            //     i += 2*k;
            // } else if (i + k <= n) {
            
            if (i + k <= n) {
                // 小于 2*k 大于 k
                reverse(arr, i, i + k - 1);
                i += 2 * k;
            } else if (i + k > n) {
                // 小于 k
                reverse(arr, i, n - 1);
                i += 2 * k;
            }
        }

        return new String(arr);
    }

    public void reverse(char[] arr, int left, int right) {
        // 专门有用来反转字符串
        while (left < right) {
            char tmp = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
            left++;
            right--;
        }
    }
    ```
