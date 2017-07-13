package com.chinaxiaopu.xiaopuMobi.vo.admin.topics;

import com.chinaxiaopu.xiaopuMobi.vo.ContextVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang on 2016/11/09.
 */
public class TopicDetailVo extends ContextVo{
    @Setter @Getter
    private Integer id;
    @Setter @Getter
    private Integer creatorId;
    @Setter @Getter
    private String nickName;
    @Setter @Getter
    private String realName;
    @Setter @Getter
    private Integer pkCnt = 0;
    @Setter @Getter
    private Integer winCnt = 0;
    @Setter @Getter
    private Integer status;
    @Setter @Getter
    private String schoolName;
    @Setter @Getter
    private String studentNo;
    @Setter @Getter
    private String mobile;
    @Setter @Getter
    private String qq;
    @Setter @Getter
    private String groups;
    @Setter @Getter
    private String slogan;
    @Setter @Getter
    private String content;
    @Setter @Getter
    private String tags;
    @Setter @Getter
    private Integer type;

    @Setter @Getter
    private Integer prizeType;
    @Setter @Getter
    private String prizeName;
    @Setter @Getter
    private Integer periodType;
    @Setter @Getter
    private String channelName;
    @Setter @Getter
    private String rule;
    @Setter @Getter
    private Integer rewardType;
    @Setter @Getter
    private String further;

    private String desc;
    private Integer challengeCnt;
    private Integer voteCnt;
    private String reason;
    private List<String> urls = new ArrayList<>();

    private Object getAttribute(String json,String attr){
        Map<String,Object> map = new Gson().fromJson(json, Map.class);
        return map.get(attr);
    }

    public String getDesc() {
        desc = (String) getAttribute(content,"desc");
        return desc;
    }

    public Integer getChallengeCnt() {
        challengeCnt = ((Double) getAttribute(rule,"challengeCnt")).intValue();
        return challengeCnt;
    }

    public Integer getVoteCnt() {
        voteCnt = ((Double) getAttribute(rule,"voteCnt")).intValue();
        return voteCnt;
    }

    public String getReason() {
        if(!StringUtils.isEmpty(further)){
            reason = (String) getAttribute(further,"reason");
        }else{
            reason = "";
        }
        return reason;
    }

    public List<String> getUrls() {
        urls = (List<String>) getAttribute(content,"urls");
        return urls;
    }
}
