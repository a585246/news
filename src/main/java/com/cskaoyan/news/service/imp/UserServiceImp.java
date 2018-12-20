package com.cskaoyan.news.service.imp;

import com.cskaoyan.news.bean.User;
import com.cskaoyan.news.mapper.UserMapper;
import com.cskaoyan.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp  implements UserService {

    @Autowired
    UserMapper userMapper;

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
}
