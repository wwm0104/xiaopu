package com.chinaxiaopu.xiaopuMobi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TopicComment extends BaseEntity {
    private Integer id;

    private Integer topicId;

    private Integer userId;

    private String userNickname;

    private String userAvatarUrl;

    private String comment;

    private Integer parentId;

    private Integer likeCnt;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date actionTime;

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

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl == null ? null : userAvatarUrl.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Integer likeCnt) {
        this.likeCnt = likeCnt;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }
}