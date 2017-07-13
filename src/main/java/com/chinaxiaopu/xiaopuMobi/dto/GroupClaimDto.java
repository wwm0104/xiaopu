package com.chinaxiaopu.xiaopuMobi.dto;


import lombok.Data;

/**
 * Created by liuwei
 * date: 16/9/29
 */
public @Data class GroupClaimDto extends ContextDto  {

    private static final long serialVersionUID = 1L;
    private Integer groupId;
    private Integer userId;
    private String userName;//用户名
    private String  studentNo;//学号
    private Long mobile;//电话
    private String qq;//QQ
}
