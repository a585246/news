package com.cskaoyan.news.service.imp;

import com.cskaoyan.news.bean.CommentVo;
import com.cskaoyan.news.bean.Comments;
import com.cskaoyan.news.mapper.CommentsMapper;
import com.cskaoyan.news.mapper.NewMapper;
import com.cskaoyan.news.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImp implements CommentService{


    @Autowired
    CommentsMapper commentsMapper;
    @Override
    public List<CommentVo> findByNid(String id) {
        return  commentsMapper.findByNid(id) ;
    }

    @Override
    public void insert(Comments comments) {
        commentsMapper.insertSelective(comments);

    }
}
