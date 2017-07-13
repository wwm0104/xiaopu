package com.chinaxiaopu.xiaopuMobi.model;

public class PrizeKey extends BaseEntity{
    private Integer id;

    private Integer createrId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }
}