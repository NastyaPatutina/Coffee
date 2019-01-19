package com.coffeegetaway.queue.utils;

import com.coffeegetaway.queue.request.Request;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUtil {
    public static void lpush(JedisPool jedisPool, String key, Request value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Gson gson = new Gson();
            jedis.lpush(key, gson.toJson(value));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static Request rpop(JedisPool jedisPool, String key) {
        Jedis jedis = null;
        try {
            Gson gson = new Gson();
            jedis = jedisPool.getResource();
            return gson.fromJson(jedis.rpop(key), Request.class);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
