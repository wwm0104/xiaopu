package com.chinaxiaopu.xiaopuMobi.model;

import java.util.Date;

public class Notify {
    private Integer id;

    private String content;

    private String type;

    private Integer targetId;

    private String targetType;

    private Integer action;

    private Integer sender;

    private Date createTime;

    private String parameter;

    private String further;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType == null ? null : targetType.trim();
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    public String getFurther() {
        return further;
    }

    public void setFurther(String further) {
        this.further = further == null ? null : further.trim();
    }
}