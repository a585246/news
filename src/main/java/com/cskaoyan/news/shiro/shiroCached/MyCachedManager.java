package com.cskaoyan.news.shiro.shiroCached;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class MyCachedManager  implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        System.out.println("myworking");
        return new ShiroCache<>();
    }
}
