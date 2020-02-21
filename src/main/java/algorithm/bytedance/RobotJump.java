package algorithm.bytedance;

import org.junit.Assert;
import org.junit.Test;

/**
 * 起初，机器人在编号为 0 的建筑处。每一步，它跳到下一个（右边）建筑。
 * 假设机器人在第 k 个建筑，且它现在的能量值是 E, 下一步它将跳到第个 k + 1 建筑。
 * 它将会得到或者失去正比于与 H(k + 1) 与 E 之差的能量。
 * 如果 H(k + 1) > E 那么机器人就失去 H(k + 1) - E 的能量值，否则它将得到 E - H(k + 1) 的能量值。
 * 游戏目标是到达第个 N 建筑，在这个过程中，能量值不能为负数个单位。现在的问题是机器人以多少能量值开始游戏，才可以保证成功完成游戏？
 *
 * @author ShaoJiale
 * Date: 2020/2/21
 */
public class RobotJump {
    public int minEnergy(int[] heights) {
        int energy = 0;
        for (int i = heights.length - 2; i >= -1; i--) {
            energy = (int) Math.ceil((energy + heights[i + 1]) / 2.0);
        }
        return energy;
    }

    @Test
    public void test() {
        // 已通过牛客网所有测试用例
        Assert.assertEquals(4, minEnergy(new int[]{3, 4, 3, 2, 4}));
        Assert.assertEquals(4, minEnergy(new int[]{4, 4, 4}));
        Assert.assertEquals(3, minEnergy(new int[]{1, 6, 4}));
    }
}
