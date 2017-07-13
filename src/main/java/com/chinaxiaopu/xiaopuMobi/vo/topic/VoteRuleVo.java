package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;
/**
 * Created by Wang on 2016/11/7.
 */
public class VoteRuleVo {
    @Setter @Getter
    private Integer topicId;
    @Setter @Getter
    private String slogan;
    @Setter @Getter
    private Integer prizeType;
    @Setter @Getter
    private String prizeName;
    @Setter @Getter @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    @Setter @Getter
    private String rule;
    @Setter @Getter
    private Integer rewardType;
    @Setter @Getter
    private Integer isValid;

    private Map<String,Object> ruleMap;

    public Map<String, Object> getRuleMap() {
        Gson gson = new Gson();
        ruleMap = gson.fromJson(rule,Map.class);
        return ruleMap;
    }
}