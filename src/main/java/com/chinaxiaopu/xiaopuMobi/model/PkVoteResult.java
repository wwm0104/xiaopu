package com.chinaxiaopu.xiaopuMobi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PkVoteResult extends PkVoteResultKey {
    private String topicSlogan;

    private Integer creatorId;

    private String creatorNickname;

    private Integer voteCnt;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    public String getTopicSlogan() {
        return topicSlogan;
    }

    public void setTopicSlogan(String topicSlogan) {
        this.topicSlogan = topicSlogan == null ? null : topicSlogan.trim();
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorNickname() {
        return creatorNickname;
    }

    public void setCreatorNickname(String creatorNickname) {
        this.creatorNickname = creatorNickname == null ? null : creatorNickname.trim();
    }

    public Integer getVoteCnt() {
        return voteCnt;
    }

    public void setVoteCnt(Integer voteCnt) {
        this.voteCnt = voteCnt;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}