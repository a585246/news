package com.cskaoyan.news.service;

import com.cskaoyan.news.bean.New;
import com.cskaoyan.news.bean.NewVo;

import java.util.List;

public interface NewService {


    List<NewVo> findNew(Integer uid);

    void insert(New aNew);

    New getNewsById(String id);

    void updateComment(Integer newsId);
}
