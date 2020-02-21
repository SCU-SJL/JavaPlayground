package algorithm.bytedance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 总共有 36 张牌，每张牌是 1 ~ 9。每个数字 4 张牌。
 * 你手里有其中的 14 张牌，如果这 14 张牌满足如下条件，即算作和牌
 * 1. 14 张牌中有 2 张相同数字的牌，称为雀头。
 * 2. 除去上述 2 张牌，剩下 12 张牌可以组成 4 个顺子或刻子。顺子的意思是递增的连续 3 个数字牌（例如234,567等），
 * 刻子的意思是相同数字的 3 个数字牌（例如 111, 777）。
 * 现在你手中有 13 张牌，请计算再抓到哪张牌你能和牌
 *
 * @author ShaoJiale
 * Date: 2020/2/21
 */
public class Mahjong {
    private static List<Integer> res;
    private static int[] cardState;
    private static int[] stateTemp;

    public List<Integer> solve(int[] cards) {
        res = new ArrayList<>();
        cardState = new int[9];
        stateTemp = new int[9];

        if (cards.length < 13) {
            return res;
        }

        for (int card : cards) {
            cardState[card - 1]++;
        }

        for (int i = 0; i < 9; i++) {
            if (cardState[i] < 4) {
                int cardNum = i + 1;
                System.arraycopy(cardState, 0, stateTemp, 0, 9);
                stateTemp[i]++;
                if (canWin(stateTemp, 14, false)) {
                    res.add(cardNum);
                }
            }
        }
        return res;
    }

    private boolean canWin(int[] cards, int leftCards, boolean hasHeader) {
        if (leftCards == 0) {
            return true;
        }

        if (!hasHeader) {
            for (int i = 0; i < 9; i++) {
                if (cards[i] >= 2) {
                    cards[i] -= 2;
                    if (canWin(cards, leftCards - 2, true)) {
                        return true;
                    }
                    cards[i] += 2;
                }
            }
            return false;
        }

        for (int i = 0; i < 9; i++) {
            if (cards[i] > 0) {
                if (cards[i] >= 3) {
                    cards[i] -= 3;
                    if (canWin(cards, leftCards - 3, true)) {
                        return true;
                    }
                    cards[i] += 3;
                }

                if (i < 7 && cards[i + 1] > 0 && cards[i + 2] > 0) {
                    cards[i]--;
                    cards[i + 1]--;
                    cards[i + 2]--;
                    if (canWin(cards, leftCards - 3, true)) {
                        return true;
                    }
                    cards[i]++;
                    cards[i + 1]++;
                    cards[i + 2]++;
                }
            }
        }
        return false;
    }

    private long startTime;
    @Before
    public void setStartTime() {
        startTime = System.currentTimeMillis();
    }

    @After
    public void getProcessTime() {
        System.out.println("Cost " + (System.currentTimeMillis() - startTime) + " ms");
    }

    @Test
    public void test() {
        {
            List<Integer> list = solve(new int[]{1, 1, 1, 1, 2, 2, 3, 3, 5, 6, 7, 8, 9});
            Integer[] ans = list.toArray(new Integer[0]);
            Assert.assertArrayEquals(ans, new Integer[]{4, 7});
        }

        {
            List<Integer> list = solve(new int[]{1, 1, 1, 2, 2, 2, 5, 5, 5, 6, 6, 6, 9});
            Integer[] ans = list.toArray(new Integer[0]);
            Assert.assertArrayEquals(ans, new Integer[]{9});
        }

        {
            List<Integer> list = solve(new int[]{1, 1, 1, 2, 2, 2, 3, 3, 3, 5, 7, 7, 9});
            Integer[] ans = list.toArray(new Integer[0]);
            Assert.assertArrayEquals(ans, new Integer[0]);
        }
    }
}
