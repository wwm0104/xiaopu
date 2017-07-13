package com.chinaxiaopu.xiaopuMobi.model;

import java.util.Date;

public class UserFans extends UserFansKey {
    private String isFocus;

    private Date foncusTime;

    private Date updateTime;

    public String getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(String isFocus) {
        this.isFocus = isFocus == null ? null : isFocus.trim();
    }

    public Date getFoncusTime() {
        return foncusTime;
    }

    public void setFoncusTime(Date foncusTime) {
        this.foncusTime = foncusTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}