package com.chenjin.common.cache.util;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisUtil
{
    private RedisTemplate<Serializable, Object> redisTemplate;

    public void remove(String[] keys)
    {
        for (String key : keys)
            remove(key);
    }

    public void removePattern(String pattern)
    {
        Set keys = this.redisTemplate.keys(pattern);
        if (keys.size() > 0)
            this.redisTemplate.delete(keys);
    }

    public void remove(String key)
    {
        if (exists(key))
            this.redisTemplate.delete(key);
    }

    public boolean exists(String key)
    {
        return this.redisTemplate.hasKey(key).booleanValue();
    }

    public Object get(String key)
    {
        Object result = null;
        ValueOperations operations = this.redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    public boolean set(String key, Object value)
    {
        boolean result = false;
        try {
            ValueOperations operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean set(String key, Object value, Long expireTime)
    {
        boolean result = false;
        try {
            ValueOperations operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            this.redisTemplate.expire(key, expireTime.longValue(), TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long increment(String key, long delta) {
        return this.redisTemplate.opsForValue().increment(key, delta).longValue();
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}