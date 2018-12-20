package com.cskaoyan.news.service;

import com.cskaoyan.news.bean.User;

public interface UserService {
    User login(User user);

    void insert(User user);

    int findUsername(String username);

    User getUserById(String id);
}
