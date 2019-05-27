package org.xij.web.core;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Configuration;
import org.xij.serialize.Serializations;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class Caches {
    private RedisProperties properties;
    private static JedisPool INSTANCE;

    public Caches(RedisProperties properties) {
        this.properties = properties;
        INSTANCE = getJedisPool();
    }

    private JedisPool getJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(properties.getJedis().getPool().getMaxActive());
        config.setMaxWaitMillis(properties.getJedis().getPool().getMaxWait().toMillis());
        return new JedisPool(config, properties.getHost(), properties.getPort(), (int) properties.getTimeout().toMillis());
    }

    public static void set(String key, Object value, int time) {
        set(key.getBytes(), value, time);
    }

    public static void set(String key, Object value) {
        set(key.getBytes(), value);
    }

    public static void set(byte[] key, Object value, int time) {
        byte[] val = Serializations.toBytes(value);
        try (Jedis jedis = INSTANCE.getResource()) {
            jedis.setex(key, time, val);
        }
    }

    public static void set(byte[] key, Object value) {
        byte[] val = Serializations.toBytes(value);
        try (Jedis jedis = INSTANCE.getResource()) {
            jedis.set(key, val);
        }
    }

    public static <T> T getObject(String key) {
        return getObject(key.getBytes());
    }

    public static <T> T getObject(String key, Class<T> clazz) {
        return getObject(key.getBytes(), clazz);
    }

    public static byte[] get(byte[] key) {
        try (Jedis jedis = INSTANCE.getResource()) {
            return jedis.get(key);
        }
    }

    public static <T> T getObject(byte[] key) {
        byte[] val = get(key);
        if (null == val)
            return null;
        return Serializations.toObject(val);
    }

    public static <T> T getObject(byte[] key, Class<T> clazz) {
        byte[] val = get(key);
        if (null == val)
            return null;
        return Serializations.toObject(val, clazz);
    }

    public static void del(byte[] key) {
        try (Jedis jedis = INSTANCE.getResource()) {
            jedis.del(key);
        }
    }

    public static void del(String key) {
        del(key.getBytes());
    }
}