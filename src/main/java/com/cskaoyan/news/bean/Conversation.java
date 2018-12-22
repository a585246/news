package com.cskaoyan.news.bean;

import java.util.Date;

public class Conversation {
    private String id;

    private Integer fromId;

    private Integer toId;

    private String conversionid;

    private Date createdDate;

    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public String getConversionid() {
        return conversionid;
    }

    public void setConversionid(String conversionid) {
        this.conversionid = conversionid == null ? null : conversionid.trim();
    }

    public Date getCreatedDate() {
        return createdDate!=null?createdDate:new Date();
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}