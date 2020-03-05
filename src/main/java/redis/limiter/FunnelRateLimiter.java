package redis.limiter;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * A rate limiter with funnel algorithm.
 *
 * @author ShaoJiale
 * Date: 2020/3/5
 */
public class FunnelRateLimiter {

    private class Funnel {
        private int capacity;
        private float leakingRate;
        private int leftQuota;
        private long leakingTs; // time of last leaking

        public Funnel(int capacity, float leakingRate){
            this.capacity = capacity;
            this.leakingRate = leakingRate;
            this.leftQuota = this.capacity;
            this.leakingTs = System.currentTimeMillis();
        }

        private void makeSpace() {
            long curTs = System.currentTimeMillis();
            long deltaTs = curTs - leakingTs;
            int deltaQuota = (int) (deltaTs * leakingRate);

            // free space overflow
            if (deltaQuota < 0) {
                this.leftQuota = capacity;
                this.leakingTs = curTs;
                return;
            }

            // free space is too small
            if (deltaQuota < 1) {
                return;
            }

            leftQuota += deltaQuota;
            leakingTs = curTs;
            leftQuota = Math.min(leftQuota, capacity);
        }

        private boolean watering(int quota) {
            makeSpace();
            if (leftQuota >= quota) {
                leftQuota -= quota;
                return true;
            }
            return false;
        }
    }

    private Map<String, Funnel> funnelMap = new HashMap<>();

    public boolean isActionAllowed(String userId, String actionKey, int capacity, float leakingRate) {
        String key = String.format("%s:%s", userId, actionKey);
        Funnel funnel = funnelMap.get(key);
        if (funnel == null) {
            funnel = new Funnel(capacity, leakingRate);
            funnelMap.put(key, funnel);
        }
        return funnel.watering(1);
    }

    @Test
    public void test() {
        FunnelRateLimiter limiter = new FunnelRateLimiter();
        for (int i = 0; i < 10; i++) {
            System.out.println(limiter.isActionAllowed("SJL", "request", 5, 1));
        }
    }
}
