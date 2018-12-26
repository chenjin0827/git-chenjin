package com.chenjin.common.cache.client;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class MyRedisTemplate extends ClientTemplate
{
    private RedisTemplate<Serializable, Object> redisTemplate;

    public void createNode(String path, Object object)
    {
        ValueOperations operations = this.redisTemplate.opsForValue();
        operations.set(path, object);
    }

    public void updateNode(String path, Object object)
    {
        ValueOperations operations = this.redisTemplate.opsForValue();
        operations.set(path, object);
    }

    public void deleteNode(String path)
    {
        if (exists(path))
            this.redisTemplate.delete(path);
    }

    public Object getNode(String path)
    {
        Object result = null;
        ValueOperations operations = this.redisTemplate.opsForValue();
        result = operations.get(path);
        return result;
    }

    public List<String> getChildren(String path)
    {
        return null;
    }

    public void createParentNode(String parentPath)
    {
    }

    private boolean exists(String key)
    {
        return this.redisTemplate.hasKey(key).booleanValue();
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }
}