package com.chinaxiaopu.xiaopuMobi.model;

public class VrActivity {
    private Integer id;

    private String appointmentDate;

    private String appointmentTime;

    private Integer appointmentCnt;

    private Integer appointmentMaxCnt;

    private Integer available;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getAppointmentCnt() {
        return appointmentCnt;
    }

    public void setAppointmentCnt(Integer appointmentCnt) {
        this.appointmentCnt = appointmentCnt;
    }

    public Integer getAppointmentMaxCnt() {
        return appointmentMaxCnt;
    }

    public void setAppointmentMaxCnt(Integer appointmentMaxCnt) {
        this.appointmentMaxCnt = appointmentMaxCnt;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}