package com.chinaxiaopu.xiaopuMobi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Topic extends BaseEntity{
    private Integer id;

    private Integer creatorId;

    private String creatorNickname;

    private String creatorAvatar;

    private Integer schoolId;

    private String schoolName;

    private Integer challengeTopicId;

    private Integer isChallenger;

    private Integer challengeId;

    private String challengeNickname;

    private String challengeAvatar;

    private Integer channelId;

    private String channelName;

    private Integer type;

    private String slogan;

    private String content;

    private Integer recommend;

    private Integer likeCnt;

    private Integer favoriteCnt;

    private Integer commentCnt;

    private Integer isOfficial;

    private Integer isDelete;

    //@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;

    private Integer isPk;

    private String further;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorNickname() {
        return creatorNickname;
    }

    public void setCreatorNickname(String creatorNickname) {
        this.creatorNickname = creatorNickname == null ? null : creatorNickname.trim();
    }

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar) {
        this.creatorAvatar = creatorAvatar == null ? null : creatorAvatar.trim();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public Integer getChallengeTopicId() {
        return challengeTopicId;
    }

    public void setChallengeTopicId(Integer challengeTopicId) {
        this.challengeTopicId = challengeTopicId;
    }

    public Integer getIsChallenger() {
        return isChallenger;
    }

    public void setIsChallenger(Integer isChallenger) {
        this.isChallenger = isChallenger;
    }

    public Integer getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Integer challengeId) {
        this.challengeId = challengeId;
    }

    public String getChallengeNickname() {
        return challengeNickname;
    }

    public void setChallengeNickname(String challengeNickname) {
        this.challengeNickname = challengeNickname == null ? null : challengeNickname.trim();
    }

    public String getChallengeAvatar() {
        return challengeAvatar;
    }

    public void setChallengeAvatar(String challengeAvatar) {
        this.challengeAvatar = challengeAvatar == null ? null : challengeAvatar.trim();
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan == null ? null : slogan.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Integer likeCnt) {
        this.likeCnt = likeCnt;
    }

    public Integer getFavoriteCnt() {
        return favoriteCnt;
    }

    public void setFavoriteCnt(Integer favoriteCnt) {
        this.favoriteCnt = favoriteCnt;
    }

    public Integer getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Integer commentCnt) {
        this.commentCnt = commentCnt;
    }

    public Integer getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(Integer isOfficial) {
        this.isOfficial = isOfficial;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getIsPk() {
        return isPk;
    }

    public void setIsPk(Integer isPk) {
        this.isPk = isPk;
    }

    public String getFurther() {
        return further;
    }

    public void setFurther(String further) {
        this.further = further == null ? null : further.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}