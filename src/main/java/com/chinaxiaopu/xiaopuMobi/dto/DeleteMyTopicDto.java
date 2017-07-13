package com.chinaxiaopu.xiaopuMobi.dto;

/**
 * Created by ycy on 2016/11/2.
 */
public class DeleteMyTopicDto extends ContextDto{
    private Integer userId;

    private Integer topicId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }
}
