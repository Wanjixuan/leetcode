# 题目
#### [345. 反转字符串中的元音字母](https://leetcode-cn.com/problems/reverse-vowels-of-a-string/)

## 思路
双指针 <br/>

首先找到前后的首个元音字母，在没有左右指针还合法的情况下，交换。

怎么找？<br/>

如果不是元音就把下标移动




## 代码

```java
    public String reverseVowels(String s) {
        char[] arr = s.toCharArray();
        int n = s.length();
        int left = 0, right = s.length() - 1;

        while (left < right) {
            // 找到前面的元音
            while (left < n && !isV(arr[left])) {
                left++;
            }
            // 找到后面的元音
            while (right > 0 && !isV(arr[right])) {
                right--;
            }
            
			// 左右指针合法
            if (left < right) {
                char tmp = arr[left];
                arr[left] = arr[right];
                arr[right] = tmp;
                left++;
                right--;
            }
        }

        return new String(arr);
    }
    public boolean isV(char s) {
        return "aeiouAEIOU".indexOf(s) >= 0;
    }
```
