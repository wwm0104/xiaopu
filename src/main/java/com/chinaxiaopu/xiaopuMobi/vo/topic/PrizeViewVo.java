package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;
/**
 * Created by Wang on 2016/11/7.
 */
public class PrizeViewVo extends ContextDto {
    @Setter @Getter
    private Integer prizeId;
    @Setter @Getter
    private String prize;
    @Setter @Getter
    private String prizeName;
    @Setter @Getter
    private Integer prizeNum;
    @Setter @Getter
    private Integer topicId;
    @Setter @Getter
    private String topicSlogan;
    @Setter @Getter
    private Integer hasTake;
    @Setter @Getter
    private String code;
    @Setter @Getter @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;
    @Setter @Getter
    private Integer challengeTopicId;
    @Setter @Getter
    private Integer pkId;
    @Getter @Setter
    private Integer rewardUserId;


    private Map<String,Object> prizeMap;

    public Map<String, Object> getPrizeMap() {
        prizeMap = new Gson().fromJson(prize,Map.class);
        return prizeMap;
    }
}