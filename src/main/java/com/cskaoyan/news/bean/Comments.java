package com.cskaoyan.news.bean;

import java.util.Date;

public class Comments {
    private Integer id;

    private Date createdDate;

    private Integer uid;

    private Integer nid;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate!=null?createdDate:new Date();
    }

    public void setCreatedDate(Date createDate) {
        this.createdDate = createdDate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}