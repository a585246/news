package com.cskaoyan.news.service.imp;

import com.cskaoyan.news.bean.New;
import com.cskaoyan.news.bean.NewVo;
import com.cskaoyan.news.mapper.NewMapper;
import com.cskaoyan.news.service.NewService;
import com.cskaoyan.news.utils.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class NewServiceimp  implements NewService {

    @Autowired
    NewMapper newMapper;

    @Override
    public List<NewVo> findNew(Integer uid) {
        List<NewVo> vos = newMapper.findNew();
        if(uid==null)
            return vos;
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

    @Override
    public void insert(New aNew) {
        newMapper.insertSelective(aNew);
    }

    @Override
    public New getNewsById(String id) {
        return newMapper.selectByPrimaryKey(Integer.parseInt(id));
    }

    @Override
    public void updateComment(Integer newsId) {
        newMapper.updateComment(newsId);
    }
}
