package com.chinaxiaopu.xiaopuMobi.dto;


import lombok.Data;

/**
 * Created by liuwei
 * date: 16/9/28
 */
public @Data class LoginUserDto extends ContextDto {

	private static final long serialVersionUID = 1L;
	
	private Long mobile;//手机号、用户名
    private String password;//密码
    private String smsCaptcha;//验证码
    private String clientDigest;//设备唯一标识
    private String registrationId;

}
