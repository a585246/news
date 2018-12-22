package com.cskaoyan.news.asynchronous;

import com.alibaba.fastjson.JSON;
import com.cskaoyan.news.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class EventProdouce {


   public  static  void  fireEvent(Event event)
    {
        String s = JSON.toJSONString(event);
        Jedis jedis = JedisUtils.getResource();
        jedis.lpush("events",s);
        jedis.close();

    }

}
