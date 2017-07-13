package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 根据图文标题搜索列表
 * Created by Administrator on 2016/11/2.
 */
@Data
public class TopicSearchVo {
    private Integer topicId;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private Integer creatorId;
    private String creatorNickname;
    private String slogan;
    private Integer likeCnt;
    private Integer favoriteCnt;
    private Integer commentCnt;
    private String expireTime;
    private String showTime;
    private Integer isLike = 0;//是否点赞 0 ： 未点赞  1 ： 点赞
    private Integer isFav = 0;//是否收藏 0 ： 为收藏 1 ： 已收藏
    private List<TopicTagsVo> groupList;
    private List<TopicTagsVo> eventList;
    private String content;
    private String creatorAvatar;
    private Map<String,Object> contentMap;
    public  Map<String,Object> getContentMap() {
        return contentMap;
    }
    private String imgsHostDomain;
    private Integer type;
    private Integer isPk;
    private String timePoint;
    private Integer isOver=1;
    private Integer userSex;
    private Integer prizeId;
    private String prizeName;
   /* public Integer getIsOver() {
        if(!StringUtils.isEmpty(expireTime) && expireTime.getTime() < new Date().getTime()){
            isOver = 0;
        }
        return isOver;
    }*/
    public  void setContentMap(String content) {
        Gson gson = new Gson();
        this.contentMap = gson.fromJson(content,Map.class);
    }


}
