package com.chinaxiaopu.xiaopuMobi.model;

import java.util.Date;

public class AwardPresen extends BaseEntity {
    private Integer id;

    private Integer userId;

    private String realName;

    private Long mobile;

    private String officialName;

    private Long officialMobile;

    private Integer awardCnt;

    private Date createTime;

    private Integer available;

    private String remarks;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName == null ? null : officialName.trim();
    }

    public Long getOfficialMobile() {
        return officialMobile;
    }

    public void setOfficialMobile(Long officialMobile) {
        this.officialMobile = officialMobile;
    }

    public Integer getAwardCnt() {
        return awardCnt;
    }

    public void setAwardCnt(Integer awardCnt) {
        this.awardCnt = awardCnt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}