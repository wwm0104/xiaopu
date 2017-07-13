package com.chinaxiaopu.xiaopuMobi.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuwei
 * date: 16/9/28
 */
public class SmsCaptchaDto extends ContextDto {

    private static final long serialVersionUID = 1L;

    private @Getter @Setter String mobile;
    private @Getter @Setter String clientDigest;
    private @Getter @Setter String smsType;

}
