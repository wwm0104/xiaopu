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
public @Data class EventListDto extends ContextDto{

    private static final long serialVersionUID = 1L;

    private String eventName;//活动名称
    private Integer schoolId;//学校Id

}
