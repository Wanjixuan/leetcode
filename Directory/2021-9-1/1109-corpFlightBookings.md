# [1109. 航班预订统计](https://leetcode-cn.com/problems/corporate-flight-bookings/)

## 思路

题目的意思就是：如果【1，4，10】-从第一个航班到第四个航班，每个航班都增加10个位置<br/>

每次有订单，其实就是一个增量，并且很重要的一点：增量是连续增加的，也就是说，这个区间内都存在这个增量！<br/>

差分数组：<br/>
差分数组的性质是，**当我们希望对原数组的某一个区间 `[l,r]` 施加一个增量`inc` 时，差分数组 `d` 对应的改变是：`d[l]` 增加 `inc`，`d[r+1]` 减少 `inc`,**
这样对于区间的修改就变为了对于两个位置的修改。并且这种**修改是可以叠加**的，即当我们多次对原数组的不同区间施加不同的增量，我们只要按规则修改差分数组即可。<br/>



### 暴力法

遍历数组，得到每个订单的起始航班和终止航班，然后加进去！

### 代码
```java
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] ret = new int[n];

        for (int[] arr : bookings) {
            int start = arr[0] - 1, end = arr[1] - 1;
            int seats = arr[2];
            for (int i = start; i <= end; i++) {
                ret[i] += seats;
            }
        }

        return ret;
    }

```

### 差分数组+前缀和

```java

    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] ret = new int[n];

        for (int[] arr : bookings) {
            int start = arr[0] - 1, end = arr[1] - 1;
            int weight = arr[2];
            // 额外的时间消耗是在每次循环加法，因为是从某个到某个都有，如果利用前缀和的思想，就只需要加在第一个，后面的不加，其余的，比如前面的没加所以也不管
            // 后面的先减去这个就可以了
            // 这种除非他全是自己到自己的，不然还是可以节省时间的
            ret[start] += weight;
            if (end+1 < n){
                // 先判断我是不是最后一个，如果不是最后一个，在我的后面减去这一个！
                ret[end+1] -= weight;
            } 
        }

        for (int i = 1; i < n; i++) {
            ret[i] = ret[i] + ret[i - 1];
        }

        return ret;
    }

```

