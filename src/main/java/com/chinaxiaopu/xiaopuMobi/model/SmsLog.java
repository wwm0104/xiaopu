package com.chinaxiaopu.xiaopuMobi.model;

import java.util.Date;

public class SmsLog extends BaseEntity{

    private Integer toMobile;

    private Integer type;

    private Date updateTime;

    public Integer getToMobile() {
        return toMobile;
    }

    public void setToMobile(Integer toMobile) {
        this.toMobile = toMobile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}