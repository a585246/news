package com.cskaoyan.news.service.imp;

import com.cskaoyan.news.bean.New;
import com.cskaoyan.news.bean.NewVo;
import com.cskaoyan.news.mapper.JedisDao;
import com.cskaoyan.news.mapper.NewMapper;
import com.cskaoyan.news.service.NewService;

import com.cskaoyan.news.utils.NewVoComparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class NewServiceimp  implements NewService {

    @Autowired
    NewMapper newMapper;
    @Autowired
    JedisDao jedisDao;
    @Override
    public List<NewVo> findNew(Integer uid) {
        List<NewVo> vos = newMapper.findNew();
        if(uid==null)
            return vos;
        List<NewVo> newVos = jedisDao.getNewVos(uid, vos);
        NewVo[] objects = (NewVo[]) newVos.toArray();
        Arrays.sort(objects,new NewVoComparator());
        List<NewVo> newVos1 = Arrays.asList(objects);
    return newVos1;
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
