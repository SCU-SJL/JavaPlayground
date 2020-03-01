package redis.lock;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Reentrant Lock in redis.
 * Create lock in redis when the lock does not exist, or add refCount for the lock.
 * Delete lock from redis when the refCount of the lock is 1.
 *
 * @author ShaoJiale
 * Date: 2020/3/1
 */
public class RedisReentrantLock {
    private ThreadLocal<Map<String, Integer>> lockers = new ThreadLocal<>();
    private Jedis jedis;

    public RedisReentrantLock(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean lock(String key) {
        Map<String, Integer> refs = this.currentLockers();
        Integer refCount = refs.get(key);
        if (refCount != null) {
            refs.put(key, refCount + 1);
            return true;
        }
        boolean ok = this.doLock(key);
        if (!ok) {
            return false;
        }

        refs.put(key, 1);
        return true;
    }

    public boolean unlock(String key) {
        Map<String, Integer> refs = this.currentLockers();
        Integer refCount = refs.get(key);
        if (refCount == null) {
            return false;
        }
        refCount--;
        if (refCount > 0) {
            refs.put(key, refCount);
        } else {
            refs.remove(key);
            this.doUnlock(key);
        }
        return true;
    }

    private boolean doLock(String key) {
        return jedis.set(key, "", "nx", "ex", 5L) != null;
    }

    private void doUnlock(String key) {
        jedis.del(key);
    }

    private Map<String, Integer> currentLockers() {
        Map<String, Integer> refs = lockers.get();
        if (refs != null) {
            return refs;
        }
        lockers.set(new HashMap<>());
        return lockers.get();
    }
}
