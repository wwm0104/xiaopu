package com.chinaxiaopu.xiaopuMobi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PkPrizeResult extends PkPrizeResultKey {
    private String code;

    private String prizeName;

    private Integer prizeType;

    private Integer prizeNum;

    private Integer challengeTopicId;

    private String challengeTopicSlogan;

    private String rewardUserNickname;

    private String rewardUserRealname;

    private String rewardUserAvatar;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date effectiveTime;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date takeTime;

    private Integer hasTake;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName == null ? null : prizeName.trim();
    }

    public Integer getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(Integer prizeType) {
        this.prizeType = prizeType;
    }

    public Integer getPrizeNum() {
        return prizeNum;
    }

    public void setPrizeNum(Integer prizeNum) {
        this.prizeNum = prizeNum;
    }

    public Integer getChallengeTopicId() {
        return challengeTopicId;
    }

    public void setChallengeTopicId(Integer challengeTopicId) {
        this.challengeTopicId = challengeTopicId;
    }

    public String getChallengeTopicSlogan() {
        return challengeTopicSlogan;
    }

    public void setChallengeTopicSlogan(String challengeTopicSlogan) {
        this.challengeTopicSlogan = challengeTopicSlogan == null ? null : challengeTopicSlogan.trim();
    }

    public String getRewardUserNickname() {
        return rewardUserNickname;
    }

    public void setRewardUserNickname(String rewardUserNickname) {
        this.rewardUserNickname = rewardUserNickname == null ? null : rewardUserNickname.trim();
    }

    public String getRewardUserRealname() {
        return rewardUserRealname;
    }

    public void setRewardUserRealname(String rewardUserRealname) {
        this.rewardUserRealname = rewardUserRealname == null ? null : rewardUserRealname.trim();
    }

    public String getRewardUserAvatar() {
        return rewardUserAvatar;
    }

    public void setRewardUserAvatar(String rewardUserAvatar) {
        this.rewardUserAvatar = rewardUserAvatar == null ? null : rewardUserAvatar.trim();
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    public Integer getHasTake() {
        return hasTake;
    }

    public void setHasTake(Integer hasTake) {
        this.hasTake = hasTake;
    }
}