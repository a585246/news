package com.cskaoyan.news.controller;

import com.cskaoyan.news.asynchronous.Event;
import com.cskaoyan.news.asynchronous.EventProdouce;
import com.cskaoyan.news.asynchronous.EventType;
import com.cskaoyan.news.bean.*;
import com.cskaoyan.news.service.CommentService;
import com.cskaoyan.news.service.NewService;
import com.cskaoyan.news.service.UserService;
import com.cskaoyan.news.utils.JedisUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import  java.util.*;
@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    NewService newService;

    @Autowired
    CommentService commentService;
@ResponseBody
    @RequestMapping("login")
    public Map login(User user, HttpSession session, HttpServletResponse resp, String rember){
        HashMap<String, Object> ret= new HashMap<>();
        ret.put("code",0);
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
            SecurityUtils.getSubject().login(token);
            SimplePrincipalCollection attribute = (SimplePrincipalCollection) session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
            User primaryPrincipal = (User) attribute.getPrimaryPrincipal();
            ret.put("data",primaryPrincipal);
            session.setAttribute("user",primaryPrincipal);
            if(rember.equals("1"))
            {
                String id = session.getId();
                session.setMaxInactiveInterval(24*60*60);
                Cookie cookie = new Cookie("JSEESIONID",id);
                resp.addCookie(cookie);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("code",1);
            ret.put("msgname","账号或密码错误");
        }
        return ret;
    }
    @ResponseBody
    @RequestMapping("reg")
    public  Map  register(User user,HttpSession session,HttpServletResponse resp)
    { HashMap<String, Object> ret= new HashMap<>();
        ret.put("code",0);
        try {
          int u= userService.findUsername(user.getUsername());
           if(u>0)
           {
               ret.put("msgname","用户名重复");
               ret.put("code",1);
               return ret;
           }
            userService.insert(user);
           login(user,session,resp,0+"");
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("code",1);
            ret.put("msgname","注册失败");
        }
        return ret;
    }

    @ResponseBody
    @RequestMapping("user/addNews")
    public HashMap<String, Object> addNews(New aNew,HttpSession session){
        HashMap<String,Object>  ret=new HashMap<>();
      User user = (User) session.getAttribute("user");
      aNew.setUid(user.getId());
      try {
            newService.insert(aNew);
            ret.put("code",0);
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("code",1);
            ret.put("msg","插入失败");
        }
        return ret;
    }

    @RequestMapping("/user/{id}")
        public String  getUser(@PathVariable String  id, Model model)
    {

        User user=userService.getUserById(id);
        model.addAttribute("user",user);
        return "personal";
    }


    @RequestMapping("/news/{id}")
    public String  getNew(@PathVariable String id,Model model)
    {

        New aNew= newService.getNewsById(id);
        Integer uid = aNew.getUid();
        List<CommentVo>  commentVos=  commentService.findByNid(id);
        User userById = userService.getUserById(uid + "");
        model.addAttribute("news",aNew);
        model.addAttribute("like",aNew.getLikeCount());
        model.addAttribute("owner",userById);
        model.addAttribute("comments",commentVos);
        return "detail";
    }

    @RequestMapping("/addComment")
    public  void addComment(Integer  newsId,String content,HttpSession session,HttpServletResponse resp) throws IOException {
        User user = (User) session.getAttribute("user");
        Comments comments = new Comments();
        comments.setNid(newsId);
        comments.setUid(user.getId());
        comments.setContent(content);
        try {
            commentService.insert(comments);
            newService.updateComment(newsId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/news/"+newsId);
    }

//点赞
    @ResponseBody
    @RequestMapping("like")
    public Map like(String newsId,HttpSession session)
    {
        HashMap ret=new HashMap();
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        Event event = new Event();
        event.setEventType(EventType.LIKE);
        event.setFromId(id+"");
        event.setNewsId(newsId);
        EventProdouce.fireEvent(event);
        Long scard =userService.likeEvent(id,newsId);
        ret.put("code",0);
        ret.put("msg",scard);
        return  ret;
    }

//点踩
    @ResponseBody
    @RequestMapping("dislike")
    public Map dislike(Integer newsId,HttpSession session)
    {
        HashMap ret=new HashMap();
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        Event event = new Event();
        event.setEventType(EventType.DISLIKE);
        event.setFromId(id+"");
        event.setNewsId(newsId+"");
        EventProdouce.fireEvent(event);
        Long scard =userService.dislikeEvent(id,newsId);
        ret.put("code",0);
        ret.put("msg",scard);
       return ret;
    }

    @RequestMapping("/user/tosendmsg")
    public String tosendMsg()
    {
        return "sendmsg";
    }

    @ResponseBody
    @RequestMapping("/user/msg/addMessage")
    public Map  addMessage(String toName,String content,HttpSession session)
    {
        HashMap ret=new HashMap();
        User user = (User) session.getAttribute("user");
        Integer fromId = user.getId();
        return userService.addMessage(toName,content,fromId);
    }
    @RequestMapping("/msg/list")
    public String  msgList(Model model,String toName,String content,HttpSession session)
    {
        HashMap ret=new HashMap();
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        List<ConversationVo>  conversations= userService.listMessage(id);
        model.addAttribute("conversations",conversations);
        return "letter";
    }
    @RequestMapping("/msg/detail")
    public String  detail(Model model,String conversationId)
    {
        HashMap ret=new HashMap();
        List<MessageVo> messages= userService.detail(conversationId);
        model.addAttribute("messages",messages);
        return "letterDetail";
    }

}
