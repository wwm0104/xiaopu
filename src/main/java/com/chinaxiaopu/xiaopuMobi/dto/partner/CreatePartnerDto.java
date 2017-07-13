package com.chinaxiaopu.xiaopuMobi.dto.partner;

import lombok.Data;

/**
 * Created by hao on 2016/11/7.
 */
@Data
public class CreatePartnerDto {
    private Integer groupId;   //社团id
    private String realName;   //真实姓名
    private Long mobile;        //手机号
    private String smsCaptcha;  //验证码
}
