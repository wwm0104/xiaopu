package com.chinaxiaopu.xiaopuMobi.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class User extends BaseEntity{

    private Long mobile;

    private Integer schoolId;

    private String shortName;

    private String password;

    private String salt;

    private Integer status;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date joinTime;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date updateTime;

    private Integer origin;

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }
}