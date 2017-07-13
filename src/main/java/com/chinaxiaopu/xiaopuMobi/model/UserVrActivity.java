package com.chinaxiaopu.xiaopuMobi.model;

import java.util.Date;

public class UserVrActivity {
    private Integer id;

    private Integer userId;

    private Integer appointmentId;

    private String appointmentDate;

    private String appointmentTime;

    private String appointmentCode;

    private Integer activityCnt;

    private Date createTime;

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

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate == null ? null : appointmentDate.trim();
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime == null ? null : appointmentTime.trim();
    }

    public String getAppointmentCode() {
        return appointmentCode;
    }

    public void setAppointmentCode(String appointmentCode) {
        this.appointmentCode = appointmentCode == null ? null : appointmentCode.trim();
    }

    public Integer getActivityCnt() {
        return activityCnt;
    }

    public void setActivityCnt(Integer activityCnt) {
        this.activityCnt = activityCnt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}