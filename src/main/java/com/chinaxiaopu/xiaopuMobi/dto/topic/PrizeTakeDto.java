package com.chinaxiaopu.xiaopuMobi.dto.topic;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;

import java.util.Date;

/**
 * Created by ycy on 2016/11/12.
 */
public class PrizeTakeDto  extends ContextDto{
    private Integer topicId;
    private Integer pkId;
    private Integer rewardUserId;
    private Integer awardUserId;
    private Date takeTime;

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    public Integer getAwardUserId() {
        return awardUserId;
    }

    public void setAwardUserId(Integer awardUserId) {
        this.awardUserId = awardUserId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getRewardUserId() {
        return rewardUserId;
    }

    public void setRewardUserId(Integer rewardUserId) {
        this.rewardUserId = rewardUserId;
    }
}
