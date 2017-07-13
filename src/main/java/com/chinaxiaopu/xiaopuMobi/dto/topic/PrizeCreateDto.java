package com.chinaxiaopu.xiaopuMobi.dto.topic;

import com.chinaxiaopu.xiaopuMobi.model.Prize;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * Created by Wang
 * date: 16/11/07
 */
public class PrizeCreateDto extends Prize{
    @Setter @Getter
    private Integer challengeCnt;//此奖品所需挑战人数
    @Setter @Getter
    private Integer voteCnt;//此奖品所需投票人数
    @Setter @Getter
    private String[] imgs;//奖品图片
    @Setter @Getter
    private String desc;//奖品简介
    @Setter @Getter
    private Integer money;//现金类别所需
    @Getter
    private Integer status;//启用状态

    private Map<String,Object> prizeMap = new HashedMap();

    public Map<String, Object> getPrizeMap() {
        Map<String,Object> map = new HashedMap();
        map.put("challengeCnt",challengeCnt);
        map.put("voteCnt",voteCnt);
        prizeMap.put("rewardRule",map);
        prizeMap.put("imgs",imgs);
        prizeMap.put("desc",desc);
        prizeMap.put("status",status);
        prizeMap.put("money",money);
        return prizeMap;
    }

    public void setStatus(Integer status) {
        this.status = status == null ? 0 : status;
    }
}