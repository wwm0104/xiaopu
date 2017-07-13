package com.chinaxiaopu.xiaopuMobi.model;

public class TopicTag {
    private Integer id;

    private Integer topicId;

    private Integer tagType;

    private Integer targetId;

    private String targetName;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getTagType() {
        return tagType;
    }

    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName == null ? null : targetName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}