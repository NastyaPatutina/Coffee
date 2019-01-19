package com.coffeegetaway.queue;

import com.coffeegetaway.queue.consumer.Consumer;
import com.coffeegetaway.queue.request.Request;
import com.coffeegetaway.queue.utils.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

import java.util.concurrent.TimeUnit;


public class JQueue {
    private JedisPool jedisPool =  new JedisPool("127.0.0.1", 6379);
    private String key = "recipes";

    private Integer length = 0;
    private int delayTime = 10;
    private TimeUnit tu = TimeUnit.SECONDS;
    private boolean isRunning = false;
    private Thread thread;

    public Pool<Jedis> getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    Runnable task = () -> {
        Request rq = JedisUtil.rpop(jedisPool, key);
        while (rq!=null) {
            if (!Consumer.run(rq)) {
                JedisUtil.lpush(jedisPool, key, rq);
                try {
                    tu.sleep(delayTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            rq = JedisUtil.rpop(jedisPool, key);
        }
        isRunning = false;
    };

    public void push(Request request){
        JedisUtil.lpush(jedisPool, key, request);
        if (!isRunning) {
            isRunning = true;
            thread = new Thread(task);
            thread.start();
        }
    }

}
