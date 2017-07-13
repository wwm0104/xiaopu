package com.chinaxiaopu.xiaopuMobi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PkResult extends PkResultKey {
    private Integer challengeTopicId;

    private String rewardUserNickname;

    private String rewardUserRealname;

    private String rewardUserAvatar;

    private Integer ranking;

    private Integer voteCnt;

    private Integer isFinish;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;

    public Integer getChallengeTopicId() {
        return challengeTopicId;
    }

    public void setChallengeTopicId(Integer challengeTopicId) {
        this.challengeTopicId = challengeTopicId;
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

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getVoteCnt() {
        return voteCnt;
    }

    public void setVoteCnt(Integer voteCnt) {
        this.voteCnt = voteCnt;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}