package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * 批量审核社团活动参加
 * Created by wuning on 2016/12/6.
 */
@Data
public class EventMemberConfirmAllDto extends ContextDto {
    private static final long serialVersionUID = 1L;

    private Integer[] memberIds;//活动人员Id
    private Integer eventId;//活动Id
    private Integer status;//活动人员的状态，2待审核，1审核通过，3审核不通过

    private Integer userId;

}
