package redis.queue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

/**
 * Message delay queue in redis.
 *
 * @author ShaoJiale
 * Date: 2020/3/1
 */
@RequiredArgsConstructor
public class RedisDelayQueue<T> {
    public static class TaskItem<T> {
        public String id;
        public T msg;
    }

    // fastJson use TypeReference to serialize generic object
    private Type TaskType = new TypeReference<TaskItem<T>>(){}.getType();
    @NonNull
    private Jedis jedis;
    @NonNull
    private String queueKey;

    public void delay(T msg) {
        TaskItem<T> task = new TaskItem<>();
        task.id = UUID.randomUUID().toString();
        task.msg = msg;
        String jsonStr = JSON.toJSONString(task);
        jedis.zadd(queueKey, System.currentTimeMillis() + 5000, jsonStr);
    }

    public void loop() {
        while (!Thread.interrupted()) {
            // only pop 1 message from zSet
            Set<String> values = jedis.zrangeByScore(queueKey, 0, System.currentTimeMillis(), 0, 1);
            if (values.isEmpty()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            String str = values.iterator().next();
            if (jedis.zrem(queueKey, str) > 0) {
                TaskItem<T> task = JSON.parseObject(str, TaskType);
                this.handleMsg(task.msg);
            }
        }
    }

    public void handleMsg(T msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        RedisDelayQueue<String> delayQueue = new RedisDelayQueue<>(jedis, "delayQueue");

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                delayQueue.delay("task" + i);
            }
        });

        Thread consumer = new Thread(delayQueue::loop);

        producer.start();
        consumer.start();

        try {
            producer.join();
            Thread.sleep(6000);
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
            // do nothing
        }
    }
}
