package algorithm.bytedance;

import org.junit.Assert;
import org.junit.Test;

/**
 * Z国的货币系统包含面值 1 元、4 元、16 元、64 元共计 4 种硬币，以及面值 1024 元的纸币。
 * 现在你使用 1024 元的纸币购买了一件价值为 1 ~ 1024 的商品，请问你最少会收到多少硬币？
 *
 * @author ShaoJiale
 * Date: 2020/2/21
 */
public class GiveChange {
    public int solve(int cost) {
        int change = 1024 - cost;
        int[] dp = new int[change + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[i] = i;
        }

        for (int i = 0; i < dp.length; i++) {
            if (i > 0) {
                dp[i] = dp[i - 1] + 1;
                if (i > 3) {
                    dp[i] = Math.min(dp[i], dp[i - 4] + 1);
                    if (i > 15) {
                        dp[i] = Math.min(dp[i], dp[i - 16] + 1);
                        if (i > 63) {
                            dp[i] = Math.min(dp[i], dp[i - 64] + 1);
                        }
                    }
                }
            }
        }
        return dp[change];
    }


    @Test
    public void test() {
        // 已通过牛客网所有测试用例
        Assert.assertEquals(1, solve(1024 - 1));
        Assert.assertEquals(1, solve(1024 - 4));
        Assert.assertEquals(1, solve(1024 - 16));
        Assert.assertEquals(1, solve(1024 - 64));
        Assert.assertEquals(2, solve(1024 - 65));
        Assert.assertEquals(0, solve(1024));
    }
}
