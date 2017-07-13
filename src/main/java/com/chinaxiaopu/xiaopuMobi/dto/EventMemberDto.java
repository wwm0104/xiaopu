package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 *
 * @author Wang
 *
 */
public @Data class EventMemberDto extends ContextDto{

    private static final long serialVersionUID = 1L;

    private Integer userId;//用户Id
    private Integer eventId;//活动Id
    private Integer status;//活动人员的状态，2待审核，1审核通过，3审核不通过
    private String keyword;//关键字，用来模糊查询

}
