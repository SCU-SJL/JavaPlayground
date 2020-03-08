package redis.transaction;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @author ShaoJiale
 * Date: 2020/3/8
 */
public class TransactionDemo {
    public static int doubleAccount(Jedis jedis, String userId) {
        String key = getKey(userId);
        while (true) {
            jedis.watch(key);
            int value = Integer.parseInt(jedis.get(key));
            value *= 2;
            Transaction tx = jedis.multi();
            tx.set(key, String.valueOf(value));
            List<Object> res = tx.exec();
            if (res != null) {
                break;
            }
        }
        return Integer.parseInt(jedis.get(key));
    }

    private static String getKey(String userId) {
        return String.format("account_%s", userId);
    }
}
