package com.cskaoyan.news.mapper;

import com.cskaoyan.news.bean.NewVo;
import com.cskaoyan.news.utils.JedisUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;
@Component
public class JedisDao {
    public List<NewVo> getNewVos(Integer uid, List<NewVo> vos) {
        Jedis jedis = JedisUtils.getResource();
        for (NewVo  vo:vos) {
            Integer id = vo.getNews().getId();
            Boolean like= jedis.sismember(id + "like", uid + "");
            Boolean dislike = jedis.sismember(id + "dislike", uid + "");
            if(like==true)
            {
                vo.setLike(1);
            }else{
                if(dislike==true)
                {
                    vo.setLike(-1);
                }
            }
        }
        jedis.close();
        return vos;
    }

    public Long getDislikeScard(Integer id, String newsId) {
        Jedis jedis =   JedisUtils.getResource();
        jedis.srem(newsId + "like", id + "");
        jedis.sadd(newsId + "dislike", id + "");
        Long scard = jedis.scard(newsId + "like");
        jedis.close();
        return scard;
    }

    public Long getLikeScard(Integer id, String newsId) {
        Jedis jedis =   JedisUtils.getResource();
        jedis.srem(newsId + "dislike", id + "");
        jedis.sadd(newsId + "like", id + "");
        Long scard = jedis.scard(newsId + "like");
        jedis.close();
        return scard;
    }
}
