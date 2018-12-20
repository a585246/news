package com.cskaoyan.news.controller;

import com.cskaoyan.news.bean.New;
import com.cskaoyan.news.bean.NewVo;
import com.cskaoyan.news.bean.User;
import com.cskaoyan.news.service.NewService;
import com.cskaoyan.news.utils.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class FormController {
    @Autowired
    NewService newService;
    @RequestMapping({"/","/home"})
    public  String form(Model model, HttpSession session)
    {
        User user = (User) session.getAttribute("user");

        List<NewVo> vos=null;
        if(user==null) {
           vos = newService.findNew(null);
            }else
      {
          Integer uid = user.getId();
          vos = newService.findNew(uid);
          model.addAttribute("pop", 0);
      }
        model.addAttribute("vos", vos);
        session.setAttribute("contextPath", "");
        model.addAttribute("cur_date", new Date());
        model.addAttribute("pop", 1);
            return "home";
    }
}
