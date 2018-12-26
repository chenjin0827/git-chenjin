package com.chenjin.common.cache.util;

import java.io.PrintStream;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;

public class EhcacheUtil
        implements InitializingBean
{
    private EhCacheCacheManager ehCacheCacheManager;
    private CacheManager manager;

    public void put(String cacheName, Object key, Object value)
    {
        System.out.println("cacheName=====" + cacheName);
        Cache cache = this.manager.getCache(cacheName);
        if (cache == null) {
            cache = new Cache(cacheName, 10000, false, false, 86400L, 3600L);
            this.manager.addCache(cache);
        }
        Element element = new Element(key, value);
        cache.put(element);
    }

    public Object get(String cacheName, Object key) {
        Cache cache = this.manager.getCache(cacheName);
        if (cache != null) {
            Element element = cache.get(key);
            return element == null ? null : element.getObjectValue();
        }

        return null;
    }

    public Cache get(String cacheName) {
        return this.manager.getCache(cacheName);
    }

    public void remove(String cacheName, Object key) {
        Cache cache = this.manager.getCache(cacheName);
        if (cache != null)
            cache.remove(key);
    }

    public void afterPropertiesSet()
            throws CacheException
    {
        this.manager = this.ehCacheCacheManager.getCacheManager();
    }

    public EhCacheCacheManager getEhCacheCacheManager() {
        return this.ehCacheCacheManager;
    }

    public void setEhCacheCacheManager(EhCacheCacheManager ehCacheCacheManager) {
        this.ehCacheCacheManager = ehCacheCacheManager;
    }
}