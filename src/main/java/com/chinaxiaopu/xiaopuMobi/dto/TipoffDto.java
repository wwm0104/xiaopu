package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * Created by hao on 2016/11/2.
 */
public @Data
class TipoffDto extends ContextDto {
    private Integer tipoffId;//话题id
    private String desc;//举报信息
    private Integer tipoffType; //举报类型：1：帖子 2 活动 3 社团
}
