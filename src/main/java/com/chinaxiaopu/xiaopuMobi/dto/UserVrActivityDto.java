package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * Created by liuwei
 * date: 2016/11/12
 */
public @Data class UserVrActivityDto extends ContextDto {

    private Integer appointmentId;

    private String appointmentDate;

    private String appointmentTime;

    private Integer activityCnt;

}
