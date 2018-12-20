package com.cskaoyan.news.service;

import com.cskaoyan.news.bean.CommentVo;
import com.cskaoyan.news.bean.Comments;

import java.util.List;

public interface CommentService {
    List<CommentVo> findByNid(String id);

    void insert(Comments comments);
}
