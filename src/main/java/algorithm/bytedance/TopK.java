package algorithm.bytedance;

import java.util.*;

/**
 * 面试题：有一亿个数，取出 top 100，尽量降低时间空间复杂度
 * 思路：使用容量为 100 的小顶堆
 */
public class TopK {
    public static List<Integer> getTop100(int[] nums) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(100, (a, b) -> a - b);
        List<Integer> res = new LinkedList<>();
        for (int num : nums) {
            if (!minHeap.contains(num)) {
                minHeap.offer(num);
                if (minHeap.size() > 100) {
                    minHeap.poll();
                }
            }
        }
        while (!minHeap.isEmpty()) {
            res.add(minHeap.poll());
        }
        return res;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int[] nums = new int[1000];
        for (int i = 0; i < 1000; i++) {
            nums[i] = random.nextInt(5000);
        }
        System.out.println(getTop100(nums));
    }
}
