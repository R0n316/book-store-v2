package ru.alex.bookstore.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheUtils {

    private final CacheManager cacheManager;

    @Autowired
    public CacheUtils(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void checkCache(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        assert cache != null;
        System.out.println(cache.getNativeCache());
    }
}
