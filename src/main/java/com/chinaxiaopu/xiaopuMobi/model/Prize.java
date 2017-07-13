package com.chinaxiaopu.xiaopuMobi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Prize extends PrizeKey {
    private String name;

    private String prize;

    private Integer type;

    private Integer isPublic;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date availableTime;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;

    private String createrRealname;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer hasLimit;

    private Integer stockTotal;

    private Integer stockOut;

    private Integer available;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize == null ? null : prize.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public Date getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(Date availableTime) {
        this.availableTime = availableTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getCreaterRealname() {
        return createrRealname;
    }

    public void setCreaterRealname(String createrRealname) {
        this.createrRealname = createrRealname == null ? null : createrRealname.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getHasLimit() {
        return hasLimit;
    }

    public void setHasLimit(Integer hasLimit) {
        this.hasLimit = hasLimit;
    }

    public Integer getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(Integer stockTotal) {
        this.stockTotal = stockTotal;
    }

    public Integer getStockOut() {
        return stockOut;
    }

    public void setStockOut(Integer stockOut) {
        this.stockOut = stockOut;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}