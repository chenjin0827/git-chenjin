package com.chenjin.common.cache.client;

import java.util.List;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;

public class MyEhcacheTemplate extends ClientTemplate
        implements InitializingBean
{
    private EhCacheCacheManager ehCacheCacheManager;
    private CacheManager manager;

    public void createNode(String path, Object object)
    {
        String[] cacheName = path.split("/");
        Cache cache = this.manager.getCache(cacheName[1]);
        if (cache == null) {
            cache = new Cache(cacheName[1], 10000, false, false, 86400L, 3600L);
            this.manager.addCache(cache);
        }
        Element element = new Element(cacheName[2], object);
        cache.put(element);
    }

    public void updateNode(String path, Object object)
    {
        String[] cacheName = path.split("/");
        Cache cache = this.manager.getCache(cacheName[1]);
        if (cache == null) {
            cache = new Cache(cacheName[1], 10000, false, false, 86400L, 3600L);
            this.manager.addCache(cache);
        }
        Element element = new Element(cacheName[2], object);
        cache.put(element);
    }

    public void deleteNode(String path)
    {
        String[] cacheName = path.split("/");
        Cache cache = this.manager.getCache(cacheName[1]);
        if (cache != null)
            cache.remove(cacheName[2]);
    }

    public Object getNode(String path)
    {
        String[] cacheName = path.split("/");
        Cache cache = this.manager.getCache(cacheName[1]);
        if (cache != null) {
            Element element = cache.get(cacheName[2]);
            return element == null ? null : element.getObjectValue();
        }

        return null;
    }

    public List<String> getChildren(String path)
    {
        return null;
    }

    public void createParentNode(String parentPath)
    {
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