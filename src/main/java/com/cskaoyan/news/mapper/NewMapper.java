package com.cskaoyan.news.mapper;

import com.cskaoyan.news.bean.New;
import com.cskaoyan.news.bean.NewVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("newMapper")
public interface NewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(New record);

    int insertSelective(New record);

    New selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(New record);

    int updateByPrimaryKey(New record);

    List<NewVo> findNew();

    New getNewsById(String id);
@Update("update news set comment_count=comment_count+1")
    void updateComment(Integer newsId);
}