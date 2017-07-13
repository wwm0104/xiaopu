package com.chinaxiaopu.xiaopuMobi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Partner extends BaseEntity{
    private Integer id;

    private Long mobile;

    private String realName;

    private Integer sex;

    private Integer schoolId;

    private String schoolName;

    private Integer starRating;

    private Integer status;

    private Integer type;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date joinTime;

    private String checkInfo;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date checkTime;

    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo == null ? null : checkInfo.trim();
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}