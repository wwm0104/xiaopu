package com.chinaxiaopu.xiaopuMobi.dto;

/**
 * Created by ycy on 2016/12/1.
 */
public class UserFansTopicLisDto extends ContextDto{
    private Integer userId;
    private String content;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
