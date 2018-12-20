package com.cskaoyan.news.controller;

import com.cskaoyan.news.service.imp.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PicController {

    @Autowired
    FileService fileService;
    @ResponseBody
    @RequestMapping("uploadImage")
    public Map upload(MultipartFile file) throws IOException {
        HashMap<String ,Object> map=new HashMap<>();
        try {
        String  url= fileService.tranToAli(file);
            map.put("code",0);
        map.put("msg",url);
        } catch (IOException e) {
            e.printStackTrace();

            map.put("code",200);
            map.put("url",null);
        }
        return map;
    }
}
