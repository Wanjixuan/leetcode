#### [443. 压缩字符串](https://leetcode-cn.com/problems/string-compression/)

思路：

首先。我得在原数组上进行修改

其次。如果出现的频率大于1，我需要存储频率在字符后面，如果频率大于10，还得拆分。



- 我需要一个指针来遍历整个数组，cur，
- 还需要在出现连续相同的时候进行计算频率。需要一个记录字串最左边的指针 left
- 因为要在原数组上进行修改，所以要一个指针来记录的写入的位置。count
- 如果频率大于10，我存进去的话需要求余，和我们想要的是反着来的，需要进行反转函数  void reverse()



简单示意（后续再补充动图）

![初始化示意图](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/443-1.png)


![初始化示意图](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/443-2.png)

![流程示意图](https://github.com/Wanjixuan/leetcode/blob/main/Pic/Question/443.gif)




```java
    public int compress(char[] chars) {
        int n = chars.length;

        if (n == 1) return 1;

        // left是记录修改之前，我每个字串最左边的位置,用来计算长度的
        //  count是记录我每次写的位置

        int count = 0, left = 0;
        for (int cur = 0; cur < n; cur++) {
            if (cur == n - 1 || chars[cur] != chars[cur + 1]) {
                //出现不一样的字母，把之前字母写进去，
                // 这时候该把数字也写进去
                chars[count++] = chars[cur];
                int num = cur - left + 1;
                if (num > 1) {
                    // 记录原始写入的位置
                    int tmpleft = count;
                    while(num > 0) {
                        // 利用count计数，同时不停的写入
                        chars[count++] = (char)(num % 10 +'0');
                        num /= 10;
                    }

                    reverse(chars, tmpleft, count - 1);
                }

                left = cur + 1;
            } 
        }
        return count;
    }

    public void reverse(char[] arr, int left, int right) {
        while (left < right) {
            char tmp = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
            left++;
            right--;
        }
    }
```



