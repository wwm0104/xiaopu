package com.chinaxiaopu.xiaopuMobi.dto;


import lombok.Data;

/**
 * Created by liuwei
 * date: 16/9/29
 */
public @Data class GroupJoinDto extends ContextDto{

    private static final long serialVersionUID = 1L;
    private Integer userId;
    private Integer groupId;

}
