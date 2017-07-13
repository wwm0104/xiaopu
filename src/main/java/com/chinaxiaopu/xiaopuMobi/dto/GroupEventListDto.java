package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * 应用在：
 *  社团列表
 *  社团信息
 *
 * @author Wang
 *
 */
public @Data class GroupEventListDto extends ContextDto {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String keyword;//关键字
    private Integer status;//状态
    private Integer timePoint;//活动时间状态：1：开始前，2：开始后，3：已结束

}
