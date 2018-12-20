package com.cskaoyan.news.bean;

import com.cskaoyan.news.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class NewVo {
    public New getNews() {
        return news;
    }

    public void setNews(New news) {
        this.news = news;
        Jedis   jedis= JedisUtils.getResource();
        Long scard = jedis.scard(news.getId() + "like");
        jedis.close();
        news.setLikeCount(scard.intValue());
    }

    New news;
    User user;

    public Integer getLike() {
        return like!=null?like:0;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    Integer like;;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
