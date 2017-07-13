package com.chinaxiaopu.xiaopuMobi.vo;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import lombok.Data;


/**
 * Created by hao on 2016/11/15.
 */
@Data
public class VrUserVo extends UserInfo {
    private String appointmentDate;
    private String appointmentTime;
    private Integer activityCnt;
}
