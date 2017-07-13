package com.chinaxiaopu.xiaopuMobi.model;

import java.util.Date;

public class Tipoff {
    private Integer id;

    private Integer tipoffId;

    private Integer tipoffType;

    private String desc;

    private Integer tipoffUserId;

    private Date tipoffTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTipoffId() {
        return tipoffId;
    }

    public void setTipoffId(Integer tipoffId) {
        this.tipoffId = tipoffId;
    }

    public Integer getTipoffType() {
        return tipoffType;
    }

    public void setTipoffType(Integer tipoffType) {
        this.tipoffType = tipoffType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Integer getTipoffUserId() {
        return tipoffUserId;
    }

    public void setTipoffUserId(Integer tipoffUserId) {
        this.tipoffUserId = tipoffUserId;
    }

    public Date getTipoffTime() {
        return tipoffTime;
    }

    public void setTipoffTime(Date tipoffTime) {
        this.tipoffTime = tipoffTime;
    }
}