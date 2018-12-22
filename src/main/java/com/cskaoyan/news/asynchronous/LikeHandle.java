package com.cskaoyan.news.asynchronous;

import com.cskaoyan.news.bean.New;
import com.cskaoyan.news.bean.User;
import com.cskaoyan.news.service.NewService;
import com.cskaoyan.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LikeHandle  implements EventHandler {
    @Autowired
    UserService userService;
    @Autowired
    NewService newService;
    @Override
    public List<EventType> register() {
        ArrayList<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(EventType.LIKE);
        return eventTypes;
    }

    @Override
    public Boolean dealEvent(Event event) {


        String newsId = event.getNewsId();
        String fromId = event.getFromId();
        User userById = userService.getUserById(fromId);
        String username = userById.getUsername();
        New newsById = newService.getNewsById(newsId);
        String title = newsById.getTitle();
        Integer uid = newsById.getUid();
        User userById1 = userService.getUserById(uid + "");
        String username1 = userById1.getUsername();
        String content=username+"给你的"+title+"点了个赞";
        Map map = userService.addMessage(username1,content,1);
        if(map.get("code")=="0")
            return true;
        return false;
    }
}
