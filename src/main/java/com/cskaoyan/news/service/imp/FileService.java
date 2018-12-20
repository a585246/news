package com.cskaoyan.news.service.imp;

import com.aliyun.oss.OSSClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
public class FileService {
    String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    String accessKeyId = "LTAIf4bRF4I3WUGf";
    String accessKeySecret = "TejSR3lYACaJQsUOLZl64fDTfuo8SY";
    String  bucket="jiang3";
    public String tranToAli(MultipartFile file) throws IOException {

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
        byte[] bytes = file.getBytes();
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        String originalFilename = file.getOriginalFilename();
        String s2 = originalFilename.substring(originalFilename.length() - 4, originalFilename.length());
        String s1 = s + s2;
        ossClient.putObject(bucket, s1, new ByteArrayInputStream(bytes));

// 关闭OSSClient。
        ossClient.shutdown();
        return "https://"+bucket+".oss-cn-shenzhen.aliyuncs.com" +"/"+s1;

    }
}
