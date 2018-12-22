package com.cskaoyan.news.service.imp;

import com.cskaoyan.news.bean.*;
import com.cskaoyan.news.mapper.ConversationMapper;
import com.cskaoyan.news.mapper.MessageMapper;
import com.cskaoyan.news.mapper.UserMapper;
import com.cskaoyan.news.service.UserService;
import com.cskaoyan.news.utils.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImp  implements UserService {
    @Autowired
   private UserMapper userMapper;
    @Autowired
    ConversationMapper conversationMapper;

    @Autowired
    MessageMapper messageMapper;
    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public int findUsername(String username) {
        return userMapper.findUsername(username);
    }

    @Override
    public User getUserById(String id) {
        return userMapper.getUserById(id);
    }

    @Override
    public Long likeEvent(Integer id, String newsId) {
        Jedis jedis =   JedisUtils.getResource();
        jedis.srem(newsId + "dislike", id + "");
        jedis.sadd(newsId + "like", id + "");
        Long scard = jedis.scard(newsId + "like");
        jedis.close();
        return scard;
    }

    @Override
    public Long dislikeEvent(Integer id, Integer newsId) {

        Jedis jedis =   JedisUtils.getResource();
        jedis.srem(newsId + "like", id + "");
        jedis.sadd(newsId + "dislike", id + "");
        Long scard = jedis.scard(newsId + "like");
        jedis.close();
        return scard;
    }

    @Override
    @Transactional
    public Map addMessage(String toName, String content, Integer fromId) {
        HashMap<String,Object>  ret=new HashMap<>();
        User user = new User();
        user.setUsername(toName);
        User toUser  = userMapper.login(user);
     if(toUser==null)
     {
         ret.put("code",1);
         ret.put("msg","查无此人请确认输入是否正确");
     }else
     {
         Integer toId = toUser.getId();
         Conversation conversation = new Conversation();
         conversation.setFromId(fromId);
         conversation.setToId(toId);
         conversation.setContent(content);
         String    conversionId= "conversion"+fromId+"and"+toId;
         conversation.setConversionid(conversionId);
         Message message = new Message();
         message.setConversionId(conversionId);
         message.setContent(content);
         message.setId(UUID.randomUUID().toString().replace("-",""));
         message.setFromId(fromId+"");
         message.setToId(toId+"");
         try {
             messageMapper.insert(message);
             Conversation conversation1 = conversationMapper.selectByPrimaryKey(conversionId);
             if(conversation1!=null)
             {
                 int i = Integer.parseInt(conversation1.getId()) + 1;

                 conversation.setId(i+"");
                 conversationMapper.updateByPrimaryKey(conversation);
             }else
             {
                 conversation.setId(1+"");
                 conversationMapper.insert(conversation);
             }
           ret.put("code",0);
         } catch (Exception e) {
             e.printStackTrace();
             ret.put("code",1);
             ret.put("msg","发送失败请重试");
         }
     }
     return ret;
    }

    @Override
    public List<ConversationVo> listMessage(Integer id) {
        return    conversationMapper.selectConversation(id);
    }

    @Override
    public List<MessageVo> detail(String id) {
        return messageMapper.selectByConversionId(id);
    }
}
