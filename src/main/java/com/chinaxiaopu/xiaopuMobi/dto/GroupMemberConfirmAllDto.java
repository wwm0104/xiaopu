package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * 参加社团申请批量审核
 * Created by wuning on 2016/12/6.
 */
@Data
public class GroupMemberConfirmAllDto extends ContextDto{
    private Integer[] memberIds;//人员Id
    private Integer groupId;
    private Integer status;//社团人员状态：1审核通过，2审核中，3审核不通过
}
