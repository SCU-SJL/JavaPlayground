package redis.util;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

/**
 * @author ShaoJiale
 * Date: 2020/3/1
 */
public abstract class JsonUtil {
    public static void set(Jedis jedis, String key, Object obj) {
        String jsonStr = JSON.toJSONString(obj);
        jedis.set(key, jsonStr);
    }

    public static String get(Jedis jedis, String key) {
        return jedis.get(key);
    }

    public static Object getAndResolve(Jedis jedis, String key, Class<?> clazz) {
        String jsonStr = jedis.get(key);
        return JSON.parseObject(jsonStr, clazz);
    }
}
