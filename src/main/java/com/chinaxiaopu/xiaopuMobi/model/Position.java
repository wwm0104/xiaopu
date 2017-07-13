package com.chinaxiaopu.xiaopuMobi.model;

import java.util.Date;

public class Position {
    private Integer id;

    private String positionName;

    private Integer type;

    private Date createTime;

    private Byte isOfficial;

    private Byte available;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName == null ? null : positionName.trim();
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

    public Byte getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(Byte isOfficial) {
        this.isOfficial = isOfficial;
    }

    public Byte getAvailable() {
        return available;
    }

    public void setAvailable(Byte available) {
        this.available = available;
    }
}