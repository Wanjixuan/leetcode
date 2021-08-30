# [881. 救生艇](https://leetcode-cn.com/problems/boats-to-save-people/)

## 思路
贪心！<br/>

首先明确：
- 每艘船只能坐两个人
-   坐一个人：我自己的体重就已经够大勒，或者！我虽然不够大，但是剩下的里面最小的那个人，我们一起坐就超过范围勒
-   坐两个人：一定是我们两个一起的时候，重量小于`limit` 

既然要船的数量最少，那么我必须要尽可能的让两个人坐一艘船！，也就是尽可能的让一艘船上坐的人体重尽量接近`limit`<br/>
如果是两个体重小的,那还有很多剩余，还是走开吧<br/>
如果尽可能让体重大，那么必定要从里面选体重最大的先坐上去！再让一个体重最小的。

所以<br/>
1、首先我要需要每次都能找到最大最小的，先排序！
2、利用双指针，分别找剩余人里面最大、最小的！
3、判断这两个人能不能一起坐上去！
- 能，那就双指针移动
- 不能，那就只移动右指针！我总得把最小的那个看看谁能带上他一起坐船吧
4、结束了一轮，必定有一艘船被安排的明明白白了！

## 代码

```java
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);

        //在排好序的情况下用双指针！
        int count = 0;
        int l = 0, r = people.length - 1;
        while (l <= r) {

            int tmpMin = people[l];
            int tmpMax = people[r];

            if (tmpMin > limit - tmpMax) {
                // 最大的只能自己坐！
                r--;
            } else {
                // 因为最多只能坐两个人，那么直接就坐下来吧
                l++;
                r--;
            }
            count++;
        }

        return count;
    }
```
