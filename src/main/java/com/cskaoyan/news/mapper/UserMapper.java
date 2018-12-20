package com.cskaoyan.news.mapper;

import com.cskaoyan.news.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Mapper
@Component
public interface UserMapper {

    @Select("SELECT  id,username,password,head_url as  headUrl from t_user where username=#{username} ")
    User login(User user);
    @Insert("insert   into  t_user(password,head_url,username)" +
            "  values  (#{password},#{headUrl},#{username}) ")
        void insert(User user);

    @Select("select count(*)  from t_user  where  username=#{username}")
    int findUsername(String username);
    @Select("SELECT  id,username,password,head_url as  headUrl from t_user where  id=#{id} ")
    User getUserById(String id);
}