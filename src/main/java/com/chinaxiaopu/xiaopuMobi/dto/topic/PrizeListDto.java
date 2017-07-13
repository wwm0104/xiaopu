package com.chinaxiaopu.xiaopuMobi.dto.topic;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;

/**
 * Created by ycy on 2016/11/30.
 */
public class PrizeListDto extends ContextDto {

    /**
     * 1：来战倒序；2：来战正序，3：投票倒序，4：投票正序
     */
    private Integer orderType;

    /**
     * 1:有机会  2：错过
     */
    private Integer isHaveChance;


    private Integer prizeType;

    private String prizeName;

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getIsHaveChance() {
        return isHaveChance;
    }

    public void setIsHaveChance(Integer isHaveChance) {
        this.isHaveChance = isHaveChance;
    }

    public Integer getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(Integer prizeType) {
        this.prizeType = prizeType;
    }
}
