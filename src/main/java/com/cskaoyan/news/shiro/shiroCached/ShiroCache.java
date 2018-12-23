package com.cskaoyan.news.shiro.shiroCached;

import com.alibaba.fastjson.JSON;
import com.cskaoyan.news.utils.JedisUtils;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import redis.clients.jedis.Jedis;

import java.util.*;

public class ShiroCache<K,V>   implements Cache<K,V>{

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    private String keyPrefix = "shiro_redis_session:";
    /**
     * 获得byte[]型的key
     * @param key
     * @return
     */
    private byte[] getByteKey(Object key){
        if(key instanceof String){
            String preKey = this. keyPrefix + key;
            return preKey.getBytes();
        }else{
            return JSON.toJSONString(key).getBytes();
        }
    }


    @Override
    public Object get(Object key) throws CacheException {

        byte[] bytes = getByteKey(key);
        Jedis jedis = JedisUtils.getResource();
        byte[] value = jedis.get(bytes);
        jedis.close();
        if(value == null){
            return null;
        }
        String s = new String(value);
        return  JSON.parse(s);
    }

    /**
     * 将shiro的缓存保存到redis中
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {

        Jedis jedis = JedisUtils.getResource();

        jedis.set(getByteKey(key), JSON.toJSONString(value).getBytes());
        jedis.close();
        byte[] bytes = jedis.get(getByteKey(key));
        Object object = JSON.parse(new String(bytes));

        return object;

    }

    @Override
    public Object remove(Object key) throws CacheException {
        Jedis jedis = JedisUtils.getResource();

        byte[] bytes = jedis.get(getByteKey(key));

        jedis.del(getByteKey(key));
        jedis.close();
        return JSON.parse(new String(bytes));
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {

    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        Jedis jedis = JedisUtils.getResource();
        Long size = jedis.dbSize();
        jedis.close();
        return size.intValue();
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
        Jedis jedis = JedisUtils.getResource();
        Set<byte[]> keys = jedis.keys(new String("shiro_redis_session*").getBytes());
        jedis.close();
        Set<Object> set = new HashSet<Object>();
        for (byte[] bs : keys) {
            set.add(JSON.parse(new String(bs)));
        }
        return set;
    }


    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();

        List<Object> values = new ArrayList<Object>();
        Jedis jedis = JedisUtils.getResource();
        for (Object key : keys) {
            byte[] bytes = jedis.get(getByteKey(key));
            values.add(JSON.parse(new String(bytes)));
        }
        jedis.close();
        return values;
    }
}
