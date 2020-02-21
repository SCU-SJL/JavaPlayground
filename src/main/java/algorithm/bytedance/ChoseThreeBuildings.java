package algorithm.bytedance;

import org.junit.Assert;
import org.junit.Test;

/**
 * 1. 我们在字节跳动大街的 N 个建筑中选定 3 个埋伏地点。
 * 2. 为了相互照应，我们决定相距最远的两名特工间的距离不超过 D 。
 * 请听题：给定 N（可选作为的建筑物数）、D（相距最远的两名特工间的距离的最大值）以及可选建筑的坐标，
 * 计算在这次行动中，大锤的小队有多少种埋伏选择。
 * <p>
 * 注意：
 * 1. 两个特工不能埋伏在同一地点
 * 2. 三个特工是等价的：即同样的位置组合(A, B, C) 只算一种埋伏方法，不能因“特工之间互换位置”而重复使用
 *
 * @author ShaoJiale
 * Date: 2020/2/18
 */
public class ChoseThreeBuildings {
    public int choices(int[] buildings, int N, int D) {
        int res = 0;
        int left = 0, right = 2;
        while (right < N) {
            if (buildings[right] - buildings[left] > D) {
                left++;
            } else if (right - left < 2) {
                right++;
            } else {
                res += getPermutations(right - left);
                right++;
            }
        }
        return res;
    }

    private int getPermutations(int sum) {
        return sum * (sum - 1) / 2;
    }

    @Test
    public void test() {
        // 已通过牛客网所有测试用例
        Assert.assertEquals(1, choices(new int[]{1, 2, 3}, 3, 3));
        Assert.assertEquals(4, choices(new int[]{1, 2, 3, 4}, 4, 3));
        Assert.assertEquals(1, choices(new int[]{1, 10, 20, 30, 50}, 5, 19));
    }
}
