package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.util.DateTimeUtil;
import com.chinaxiaopu.xiaopuMobi.vo.ContextVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.*;
/**
 * Created by Wang on 2016/11/7.
 */
public class TopicVo extends ContextVo{

    @Setter @Getter
    private Integer topicId;
    @Setter @Getter
    private Integer challengeTopicId;
    @Setter @Getter
    private Integer creatorId;
    @Setter @Getter
    private String creatorNickname;
    @Setter @Getter
    private String creatorAvatar;
    @Setter @Getter
    private String content;
    private Map<String,Object> contentMap;
    @Setter @Getter
    private Integer isPk;
    @Setter @Getter
    private Integer isFav = 0;
    @Setter @Getter
    private Integer isLike = 0;
    @Setter @Getter
    private Integer type;
    @Setter @Getter
    private Integer isOfficial;
    @Setter @Getter
    private Date createTime;
    @Setter @Getter
    private List<TopicTagsVo> eventList;
    @Setter @Getter
    private List<TopicTagsVo> groupList;
    @Setter @Getter
    private Integer likeCnt;
    @Setter @Getter
    private Integer favoriteCnt;
    @Setter @Getter
    private Integer commentCnt;
    @Setter @Getter
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;
    @Setter @Getter
    private Integer voteCnt = 0;
    private String showTime;
    private Integer isOver = 1;
    @Setter @Getter
    private Integer topicRank;
    @Setter @Getter
    private String slogan;
    @Setter @Getter
    private Integer userSex;
    @Setter @Getter
    private Integer isChallenger;
    @Setter @Getter
    private Integer prizeId;
    @Setter @Getter
    private String prizeName;
    @Setter @Getter
    private Integer joinCnt; //参与人数
    @Setter @Getter
    private Integer isFocus;
    @Setter @Getter
    private Integer isFirst;
    @Setter @Getter
    private List<UserPkVo> userList;


    public String getShowTime() {
        showTime = DateTimeUtil.getShowTime(createTime);
        return showTime;
    }

    public Integer getIsOver() {
        if(!StringUtils.isEmpty(expireTime) && expireTime.getTime() < new Date().getTime()){
            isOver = 0;
        }
        return isOver;
    }

    public  Map<String,Object> getContentMap() {
        return contentMap;
    }

    public  void setContentMap(String content) {
        Gson gson = new Gson();
        this.contentMap = gson.fromJson(content,Map.class);
    }

}