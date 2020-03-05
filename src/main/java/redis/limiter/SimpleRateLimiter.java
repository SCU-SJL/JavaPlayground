package redis.limiter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;

/**
 * A simple limiter of rate.
 *
 * @author ShaoJiale
 * Date: 2020/3/5
 */
@RequiredArgsConstructor
public class SimpleRateLimiter {
    @NonNull
    private Jedis jedis;

    /**
     * check if the current action is illegal.
     *
     * @param userId    user id
     * @param actionKey key of the current action
     * @param period    time period (seconds)
     * @param maxCount  max allowed actions in the period
     * @return true when the action is allowed
     */
    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) {
        // actual key in zSet
        String key = String.format("hist:%s:%s", userId, actionKey);
        long curTime = System.currentTimeMillis();

        Pipeline pipeline = jedis.pipelined();
        pipeline.multi();
        pipeline.zadd(key, curTime, "" + curTime);

        // remove elements before the window
        pipeline.zremrangeByScore(key, 0, curTime - period * 1000);
        Response<Long> count = pipeline.zcard(key);
        pipeline.expire(key, period + 1);
        pipeline.exec();

        try {
            pipeline.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count.get() <= maxCount;
    }

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("localhost", 6379);
        SimpleRateLimiter limiter = new SimpleRateLimiter(jedis);
        for (int i = 0; i < 10; i++) {
            System.out.println(limiter.isActionAllowed("SJL", "request", 2, 5));
            Thread.sleep(2000);
        }
    }
}
