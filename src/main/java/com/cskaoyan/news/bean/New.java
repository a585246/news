package com.cskaoyan.news.bean;

import java.util.Date;

public class New {
    private Integer id;

    private String title;

    private Integer commentCount;

    private String image;

    private String link;

    private Date createdDate;

    private Integer likeCount;

    private Integer uid;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getCommentCount() {
        return commentCount!=null?commentCount:0;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public Date getCreatedDate() {
        return createdDate!=null?createdDate:new Date();
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getLikeCount() {
        return likeCount!=null?likeCount:0;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}