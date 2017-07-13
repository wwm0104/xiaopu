package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * Created by Wang on 2016/10/17.
 */
public @Data
class GroupMemberConfirmDto extends ContextDto{

    private Integer memberId;//人员Id
    private Integer groupId;
    private Integer status;//社团人员状态：1审核通过，2审核中，3审核不通过
    private String memo;//审核不通过的原因

    private Integer userId;
}
