### 思路
- 出现一次
- 出现一次里得第一个
首先肯定要把所有的字符串中字符都遍历一遍，并且要判断是否多次出现，使用一个 HashMap就可以，将字符作为 Key，判断后序是否存在这个 Key
- 如果不存在，至少目前看来是第一次遇见这个字符，map.put,同时统计出现一次的 LinkedHashSet中加入这个字符
- 如果存在，说明出现过一次了，再把set中的这个字符给移出去，（有可能set中已经没有了，因为出现多次的话在第二次就无了）
- 同时LinkedHashSet有序，第一个就是我放进去的第一个仅出现一次的

### 代码


```java
class Solution {
    public char firstUniqChar(String s) {
        
        // 首先要完全遍历一遍
        // 其次要统计每个元素出现的次数
        // 存储的时候需要是无序的
        if (s.length() < 1) return ' ';
        Map<Character, Integer> map = new LinkedHashMap<>();
        Set<Character> set = new LinkedHashSet<>();
        char[] chars = s.toCharArray();
        for (char ch : chars) {

            if (!map.containsKey(ch)) {
                map.put(ch, map.getOrDefault(ch, 0) + 1);
                set.add(ch);
            } else {
                set.remove(ch);
            }
        }


        return set.isEmpty() ? ' ' : set.iterator().next();
    }
}
```
