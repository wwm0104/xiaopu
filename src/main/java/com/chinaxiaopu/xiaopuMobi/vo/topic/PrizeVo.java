package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.model.Prize;
import com.google.gson.Gson;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by ycy on 2016/11/7.
 */
public class PrizeVo extends Prize{
    private Integer challengeCnt;

    private Integer voteCnt;

    private String[] imgs;

    private String desc;

    private  Integer sort=0;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    private Map<String,Object> prizeMap;

    public Integer getChallengeCnt() {
        return challengeCnt;
    }

    public void setChallengeCnt(Integer challengeCnt) {
        this.challengeCnt = challengeCnt;
    }

    public Integer getVoteCnt() {
        return voteCnt;
    }

    public void setVoteCnt(Integer voteCnt) {
        this.voteCnt = voteCnt;
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String, Object> getPrizeMap() {
        if(!StringUtils.isEmpty(getPrize())){
            Gson gson = new Gson();
            prizeMap = gson.fromJson(getPrize(),Map.class);
        }
        return prizeMap;
    }
}
