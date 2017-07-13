package com.chinaxiaopu.xiaopuMobi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TopicLike {
    private Integer id;

    private Integer topicId;

    private Integer userId;

    private String userNickname;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date likeTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname == null ? null : userNickname.trim();
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }
}