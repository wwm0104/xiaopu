package com.chinaxiaopu.xiaopuMobi.model;

import java.util.Date;

public class UserFocusAnchor extends BaseEntity {
    private Integer id;

    private Integer userId;

    private Integer focusUserId;

    private Date focusTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFocusUserId() {
        return focusUserId;
    }

    public void setFocusUserId(Integer focusUserId) {
        this.focusUserId = focusUserId;
    }

    public Date getFocusTime() {
        return focusTime;
    }

    public void setFocusTime(Date focusTime) {
        this.focusTime = focusTime;
    }
}