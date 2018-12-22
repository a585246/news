package com.cskaoyan.news.service;

import com.cskaoyan.news.bean.ConversationVo;
import com.cskaoyan.news.bean.Message;
import com.cskaoyan.news.bean.MessageVo;
import com.cskaoyan.news.bean.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User login(User user);

    void insert(User user);

    int findUsername(String username);

    User getUserById(String id);

    Long likeEvent(Integer id, String newsId);

    Long dislikeEvent(Integer id, Integer newsId);

    Map addMessage(String toName, String content, Integer fromId);

    List<ConversationVo> listMessage(Integer id);

    List<MessageVo> detail(String id);
}
