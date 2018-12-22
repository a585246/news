package com.cskaoyan.news.asynchronous;

import com.alibaba.fastjson.JSON;
import com.cskaoyan.news.utils.JedisUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.*;
@Component
public class EventCousumer implements ApplicationContextAware ,InitializingBean{
    private  ApplicationContext applicationContext;
  private   HashMap<EventType, List<EventHandler>> ret = new HashMap<>();
    public  void getEvent(){
        Jedis jedis = JedisUtils.getResource();
        List<String> events = jedis.brpop(0, "events");
        jedis.close();
        String s = events.get(1);
        Event event = JSON.parseObject(s, Event.class);
        List<EventHandler> eventHandlers = ret.get(event.eventType);

        for (EventHandler eventHandler:eventHandlers) {
                    eventHandler.dealEvent(event);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> eventHandleres = applicationContext.getBeansOfType(EventHandler.class);
        Collection<EventHandler> values = eventHandleres.values();

        for (EventHandler eventhand:values) {
            List<EventType> registers = eventhand.register();
            for (EventType eventtype:registers) {
                List<EventHandler> eventHandlers = ret.get(eventtype);
                if(eventHandlers==null)
                {
                    ArrayList<EventHandler> eventHanlers = new ArrayList<>();
                    eventHanlers.add(eventhand);
                    ret.put(eventtype,eventHanlers);
                }
                else{
                    eventHandlers.add(eventhand);
                }
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                   getEvent();
                }
            }
        }).start();
    }
}
