package com.chinaxiaopu.xiaopuMobi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.common.util.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Event extends AbstractModelWithImgs{

    private Integer creatorId;

    private Integer organizeId;

    private String organizeName;

    private String subject;

    private String description;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date endTime;

    private String address;

    private Integer status;

    private Integer joinType;

    private Integer allowMultiGroups;

    private String qrcode;

    private Integer joinCnt;

    private Integer followCnt;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getJoinType() {
        return joinType;
    }

    public void setJoinType(Integer joinType) {
        this.joinType = joinType;
    }

    public Integer getAllowMultiGroups() {
        return allowMultiGroups;
    }

    public void setAllowMultiGroups(Integer allowMultiGroups) {
        this.allowMultiGroups = allowMultiGroups;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    public Integer getJoinCnt() {
        return joinCnt;
    }

    public void setJoinCnt(Integer joinCnt) {
        this.joinCnt = joinCnt;
    }

    public Integer getFollowCnt() {
        return followCnt;
    }

    public void setFollowCnt(Integer followCnt) {
        this.followCnt = followCnt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}