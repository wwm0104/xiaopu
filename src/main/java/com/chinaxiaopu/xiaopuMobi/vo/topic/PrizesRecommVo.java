package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.google.gson.Gson;
import lombok.Data;

import java.util.Map;

/**
 * 首页奖品推荐
 * Created by wuning on 2016/11/30.
 */
@Data
public class PrizesRecommVo{
    private  Integer prizeId;
    private String prizeName;
    private String prize;
    private Map<String,Object> prizeMap;//奖品信息  转 json
    public Map<String, Object> getPrizeMap() {
        return prizeMap;
    }
    public void setPrizeMap(String prize) {
        Gson gson = new Gson();
        this.prizeMap = gson.fromJson(prize,Map.class);
    }




}
