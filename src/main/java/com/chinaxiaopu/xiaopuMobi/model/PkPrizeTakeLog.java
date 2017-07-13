package com.chinaxiaopu.xiaopuMobi.model;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PkPrizeTakeLog extends ContextDto {
    private Integer id;

    private Integer awardUserId;

    private Integer pkId;

    private Integer rewardUserId;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date takeTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAwardUserId() {
        return awardUserId;
    }

    public void setAwardUserId(Integer awardUserId) {
        this.awardUserId = awardUserId;
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

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }
}