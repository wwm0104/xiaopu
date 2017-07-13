package com.chinaxiaopu.xiaopuMobi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RecommendEvent extends BaseEntity{
    private Integer id;

    private Integer eventId;

    private Integer organizeId;

    private String organizeName;

    private String eventSubject;

    private String posterImg;

    private Integer sort;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date recommendTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(Integer organizeId) {
        this.organizeId = organizeId;
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName == null ? null : organizeName.trim();
    }

    public String getEventSubject() {
        return eventSubject;
    }

    public void setEventSubject(String eventSubject) {
        this.eventSubject = eventSubject == null ? null : eventSubject.trim();
    }

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg == null ? null : posterImg.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(Date recommendTime) {
        this.recommendTime = recommendTime;
    }
}