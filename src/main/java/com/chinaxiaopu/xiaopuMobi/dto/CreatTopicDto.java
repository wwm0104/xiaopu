package com.chinaxiaopu.xiaopuMobi.dto;

import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.AudioContent;
import com.chinaxiaopu.xiaopuMobi.dto.topic.Content;
import com.chinaxiaopu.xiaopuMobi.dto.topic.EventTopicDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.GroupTopicDto;
import com.chinaxiaopu.xiaopuMobi.model.Event;
import com.chinaxiaopu.xiaopuMobi.model.Group;
import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;

import java.util.Date;
import java.util.List;

/**
 * Created by ycy on 2016/11/2.
 */
public class CreatTopicDto extends ContextDto {

    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date onlineTime;

    private int[] eventIdList;
    /**
     * 活动列表
     */
    private List<EventTopicDto> eventList;
    /**
     * 社团列表
     */
    private List<GroupTopicDto> groupList;
    /**
     * PK时间类型
     */
    private Integer periodType;
    /**
     * 奖品ID
     */
    private Integer prizeId;
    /**
     * 奖励类型（原则），默认1 最高者得
     */
    private Integer rewardType;
    /**
     * 规则
     */
    private String rule;

    private Date rewardExpireTime;

    private Integer userId;

    private Integer days;

    private Integer id;

    private Integer creatorId;

    private String creatorNickname;

    private String creatorAvatar;

    private Integer schoolId;

    private String schoolName;

    private Integer challengeTopicId;

    private Integer isChallenger;

    private Integer challengeId;

    private String challengeNickname;

    private String challengeAvatar;

    private Integer channelId;

    private String channelName;

    private Integer type;

    private String slogan;

    private String content;

    private Content contentJSON;

    private AudioContent audioContentJSON;

    private Integer recommend;

    private Integer likeCnt;

    private Integer favoriteCnt;

    private Integer commentCnt;

    private Integer isOfficial;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

    private Date expireTime;

    private Integer isPk;

    private String further;

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public AudioContent getAudioContentJSON() {
        return audioContentJSON;
    }

    public void setAudioContentJSON(AudioContent audioContentJSON) {
        this.audioContentJSON = audioContentJSON;
    }

    public int[] getEventIdList() {
        return eventIdList;
    }

    public void setEventIdList(int[] eventIdList) {
        this.eventIdList = eventIdList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar) {
        this.creatorAvatar = creatorAvatar == null ? null : creatorAvatar.trim();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public Integer getChallengeTopicId() {
        return challengeTopicId;
    }

    public void setChallengeTopicId(Integer challengeTopicId) {
        this.challengeTopicId = challengeTopicId;
    }

    public Integer getIsChallenger() {
        return isChallenger;
    }

    public void setIsChallenger(Integer isChallenger) {
        this.isChallenger = isChallenger;
    }

    public Integer getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Integer challengeId) {
        this.challengeId = challengeId;
    }

    public String getChallengeNickname() {
        return challengeNickname;
    }

    public void setChallengeNickname(String challengeNickname) {
        this.challengeNickname = challengeNickname == null ? null : challengeNickname.trim();
    }

    public String getChallengeAvatar() {
        return challengeAvatar;
    }

    public void setChallengeAvatar(String challengeAvatar) {
        this.challengeAvatar = challengeAvatar == null ? null : challengeAvatar.trim();
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan == null ? null : slogan.trim();
    }

//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content == null ? null : content.trim();
//    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Content getContentJSON() {
        return contentJSON;
    }

    public void setContentJSON(Content contentJSON) {
        this.contentJSON = contentJSON;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Integer likeCnt) {
        this.likeCnt = likeCnt;
    }

    public Integer getFavoriteCnt() {
        return favoriteCnt;
    }

    public void setFavoriteCnt(Integer favoriteCnt) {
        this.favoriteCnt = favoriteCnt;
    }

    public Integer getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Integer commentCnt) {
        this.commentCnt = commentCnt;
    }

    public Integer getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(Integer isOfficial) {
        this.isOfficial = isOfficial;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getIsPk() {
        return isPk;
    }

    public void setIsPk(Integer isPk) {
        this.isPk = isPk;
    }

    public String getFurther() {
        return further;
    }

    public void setFurther(String further) {
        this.further = further == null ? null : further.trim();
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getRewardExpireTime() {
        return rewardExpireTime;
    }

    public void setRewardExpireTime(Date rewardExpireTime) {
        this.rewardExpireTime = rewardExpireTime;
    }

    public List<EventTopicDto> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventTopicDto> eventList) {
        this.eventList = eventList;
    }

    public List<GroupTopicDto> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupTopicDto> groupList) {
        this.groupList = groupList;
    }

    public Integer getPeriodType() {
        return periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public Integer getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Integer prizeId) {
        this.prizeId = prizeId;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
