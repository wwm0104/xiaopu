package com.chinaxiaopu.xiaopuMobi.dto;

/**
 * Created by ycy on 2016/12/1.
 */
public class UserFansDto extends ContextDto{
    private Integer userId;
    private Integer fansId;
    private Integer Type;

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFansId() {
        return fansId;
    }

    public void setFansId(Integer fansId) {
        this.fansId = fansId;
    }
}
