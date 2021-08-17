  [551. 学生出勤记录 I](https://leetcode-cn.com/problems/student-attendance-record-i/)
  
  ## 题目
 给你一个字符串 s 表示一个学生的出勤记录，其中的每个字符用来标记当天的出勤情况（缺勤、迟到、到场）。记录中只含下面三种字符： <br/>

`'A'`：`Absent`，缺勤 <br/>
`'L'`：`Late`，迟到   <br/>
`'P'`：`Present`，到场 <br/>
如果学生能够 同时 满足下面两个条件，则可以获得出勤奖励：<br/>

- 按 总出勤 计，学生缺勤（`'A'`）严格 少于两天。
- 学生 不会 存在 连续 3 天或 3 天以上的迟到（`'L'`）记录。
- 如果学生可以获得出勤奖励，返回 `true` ；否则，返回 `false` 。 <br/>

> 输入：s = "PPALLP"
> 输出：true
> 解释：学生缺勤次数少于 2 次，且不存在 3 天或以上的连续迟到记录。 <br/>


## 代码


  
  ```java
    public boolean checkRecord(String s) {
        
        int n = s.length();

        int countA = 0;
        int countL = 0;
   
        for (int i = 0; i < n; i++) {
            char tmp = s.charAt(i);
            if (tmp == 'A') {
                countA++;
                if (countA >= 2) return false;
                // 这里为什么不能加continue,会报错
                // 因为当前不是L，所以为了满足连续的那个条件，需要后面把L的计数器清零。
                // 所以不能用 continue;
                // continue;
            }
            if (tmp == 'L') {
     
                countL++;
                // 三天以上
                if (countL >= 3) return false;
            } else {
                countL = 0;
            }

        }
        return true;
    }
    ```
