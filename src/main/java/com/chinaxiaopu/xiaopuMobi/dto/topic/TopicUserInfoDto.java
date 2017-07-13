package com.chinaxiaopu.xiaopuMobi.dto.topic;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by ycy on 2016/11/12.
 */
public class TopicUserInfoDto extends UserInfo{

    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date joinTime;
    private Integer reviewStatus;
    private Integer groupId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }
}
