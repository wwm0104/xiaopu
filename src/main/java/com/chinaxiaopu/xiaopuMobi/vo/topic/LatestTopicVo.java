package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.vo.ContextVo;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Wang on 2016/11/7.
 */
public class LatestTopicVo extends ContextVo {
    @Setter @Getter
    private Integer topicId;
    @Setter @Getter
    private String slogan;
    @Setter @Getter
    private Integer type;
    @Setter @Getter
    private Integer creatorId;
    @Setter @Getter
    private String creatorName;
    @Setter @Getter
    private String content;
    @Setter @Getter
    private Integer isOfficial;
    @Setter @Getter
    private Date expireTime;
    @Setter @Getter
    private Integer prizeId;
    @Setter @Getter
    private String prizeName;

    private Integer isOver = 1;
    public Integer getIsOver() {
        if(!StringUtils.isEmpty(expireTime) && expireTime.getTime() < new Date().getTime()){
            isOver = 0;
        }
        return isOver;
    }

    @Setter
    private Map<String,Object> contentMap = new HashMap();

    public Map<String, Object> getContentMap() {
        contentMap = new Gson().fromJson(content,Map.class);
        return contentMap;
    }
}