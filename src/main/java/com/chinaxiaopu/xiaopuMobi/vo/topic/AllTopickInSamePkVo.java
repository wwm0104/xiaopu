package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by ycy on 2016/11/8.
 */
public class AllTopickInSamePkVo {
    private Integer userId;
    private String nickName;
    private String realName;
    private Integer type;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date createTime;
    private Integer recommend;
    private Integer voteCnt;
    private Integer topicId;
    private Integer pkId;
    private Integer tipoffSum;
    private String tags;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getVoteCnt() {
        return voteCnt;
    }

    public void setVoteCnt(Integer voteCnt) {
        this.voteCnt = voteCnt;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getTipoffSum() {
        return tipoffSum;
    }

    public void setTipoffSum(Integer tipoffSum) {
        this.tipoffSum = tipoffSum;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
