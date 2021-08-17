  [551. 学生出勤记录 I](https://leetcode-cn.com/problems/student-attendance-record-i/)
  
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
