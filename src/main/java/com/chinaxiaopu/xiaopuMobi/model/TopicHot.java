package com.chinaxiaopu.xiaopuMobi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TopicHot {
    private Integer topicId;

    private Integer type;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
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
}