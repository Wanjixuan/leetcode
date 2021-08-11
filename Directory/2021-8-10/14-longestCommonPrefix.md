### 思路
整体来看，就是每个的第一个字符先比，再比第二个字符这样。纵向扫描
局部来看，先确定一个字符串，再比较后面的字符串与自己是否有公共前缀
- 有：继续遍历
- 没有：直接退出循环，此时的公共前缀就出来了



#### 横向扫描
```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        
        if (strs.length == 0 || strs == null) return "";
        
        int n = strs.length;
        // 确定一个基础的字符串
        String preStr=strs[0];
        for (int i = 1; i < n; i++) {
            // 比较后面字符串与现在字符串的公共部分
            preStr = compare(strs[i], preStr);
            
            if (preStr.length() == 0) {
            // 如果没有公共部分，直接退出
                break;
            } 
        }
        return preStr;
    }

    public String compare(String s1, String s2) {

        int minLen = Math.min(s1.length(), s2.length());
        int i = 0;
        while (i < minLen && s1.charAt(i) == s2.charAt(i)) {
            i++;
        }
        return s1.substring(0, i);
    }
}
```

### 纵向扫描

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        
        if (strs.length == 0 || strs == null) return "";

        int n = strs.length;
        int perN = strs[0].length();

        for (int i = 0; i < perN; i++) {
            // 按列取值
            char c = strs[0].charAt(i);
            for (int j = 0; j < n; j++) {
                // 这里有一点需要考虑，这时候的列值，是否已经到了某个字符串的结尾了，并不需要一开始就知道最小的部分
                // 边扫描边确认
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }

        return strs[0];
    }
}
```
