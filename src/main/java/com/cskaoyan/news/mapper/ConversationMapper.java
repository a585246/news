package com.cskaoyan.news.mapper;

import com.cskaoyan.news.bean.Conversation;
import com.cskaoyan.news.bean.ConversationVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ConversationMapper {
    int deleteByPrimaryKey(String id);

    int insert(Conversation record);

    int insertSelective(Conversation record);

    Conversation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Conversation record);

    int updateByPrimaryKeyWithBLOBs(Conversation record);

    int updateByPrimaryKey(Conversation record);

    List<ConversationVo> selectConversation(Integer id);
}