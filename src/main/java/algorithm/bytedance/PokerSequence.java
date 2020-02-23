package algorithm.bytedance;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 你手中有一堆牌，你将以下面的方式将牌放到你面前的桌面上
 * 1. 将牌堆顶部的一张牌放到桌面上的牌堆顶
 * 2. 将牌堆顶部的一张牌放到你手中的牌堆底
 * 3. 重复上述步骤直到你的牌全部放在桌上
 * 问题：已知桌面牌堆从上到下的序列，求原来你手中牌堆从上到下的序列
 * 例如：桌面牌自上而下为 4, 2, 3, 1 -> 你手中初始牌堆自上而下为 1, 2, 3, 4
 *
 * @author ShaoJiale
 * Date: 2020/2/22
 */
public class PokerSequence {
    public static List<Integer> sort(int[] pokers) {
        LinkedList<Integer> table = new LinkedList<>();
        for (int poker : pokers) {
            table.add(poker);
        }

        LinkedList<Integer> hand = new LinkedList<>();
        for (Integer poker : table) {
            // 将牌堆底的牌放到顶部
            if (hand.size() > 1) {
                hand.offer(hand.pollLast());
            }
            // 从桌面取回牌
            hand.offer(poker);
        }

        return new ArrayList<>(hand);
    }
}
