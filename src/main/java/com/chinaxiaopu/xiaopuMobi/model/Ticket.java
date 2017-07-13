package com.chinaxiaopu.xiaopuMobi.model;

import java.util.Date;

public class Ticket {
    private Integer id;

    private String ticketName;

    private String description;

    private Integer businessId;

    private Integer businessType;

    private Integer ticketCnt;

    private Integer remainingCnt;

    private Integer createId;

    private Date createTime;

    private Date expireTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName == null ? null : ticketName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getTicketCnt() {
        return ticketCnt;
    }

    public void setTicketCnt(Integer ticketCnt) {
        this.ticketCnt = ticketCnt;
    }

    public Integer getRemainingCnt() {
        return remainingCnt;
    }

    public void setRemainingCnt(Integer remainingCnt) {
        this.remainingCnt = remainingCnt;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}