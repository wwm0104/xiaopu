package com.chinaxiaopu.xiaopuMobi.model;

import java.util.Date;

public class TopicPlay {
    private Integer topicId;

    private Integer playCnt;

    private Date updateTime;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getPlayCnt() {
        return playCnt;
    }

    public void setPlayCnt(Integer playCnt) {
        this.playCnt = playCnt;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}