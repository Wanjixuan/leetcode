// 核心是贪心算法，每次都对最大的石头堆进行操作。
// 关键问题，如何每次都找到最大的？ 直接用数组遍历不太行，因为即使排序后选择最大，也会前后不能相顾
// 优先队列可以很好的解决这个问题

class Solution {
    public int minStoneSum(int[] piles, int k) {
                //匿名内部类
//        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o2 - o1;
//            }
//        });

        // lambda

        PriorityQueue<Integer> pq = new PriorityQueue<>( (o1, o2) -> {return o2 - o1;});
        int sum = 0;

        for (int pile : piles) {
            pq.offer(pile);
            sum += pile;
        }

        while ((k--)>0) {
            int tmp = pq.peek();
            pq.poll();
            sum -= tmp / 2;
            pq.offer(tmp - tmp / 2);
        }
        return sum;

    }
}
