package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * Created by Wang on 2016/10/17.
 */
public @Data
class GroupMemberDto extends ContextDto{

    private Integer userId;
    private Integer groupId;
    private Integer status;//社团人员状态：1审核通过，2审核中，3审核不通过
    private String keyword;//关键字，用来模糊查询
}
