package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 *
 * @author Wang
 *
 */
public @Data class EventMemberConfirmDto extends ContextDto{

    private static final long serialVersionUID = 1L;

    private Integer memberId;//活动人员Id
    private Integer eventId;//活动Id
    private Integer status;//活动人员的状态，2待审核，1审核通过，3审核不通过
    private String memo;//驳回原因

    private Integer userId;

}
