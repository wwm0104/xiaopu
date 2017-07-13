package com.chinaxiaopu.xiaopuMobi.dto.topic;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import lombok.Data;

/**
 * Created by Wang
 * date: 16/11/02
 */
public @Data class PrizeDto extends ContextDto{
    private Integer type;//奖品类型
    private Integer status;//启用状态
    private Integer isPublic;//是否为公开的
    private Integer sort;//排序字段
}
