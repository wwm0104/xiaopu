package com.chinaxiaopu.xiaopuMobi.dto;

/**
 * Created by ycy on 2016/11/2.
 */
public class MyTopicParam extends ContextDto{
    private Integer userId;

    private Integer myUserId;

    public Integer getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(Integer myUserId) {
        this.myUserId = myUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
