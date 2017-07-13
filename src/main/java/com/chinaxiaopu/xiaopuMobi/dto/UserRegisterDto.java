package com.chinaxiaopu.xiaopuMobi.dto;


import lombok.Data;

/**
 * 
 * @author Wang
 *
 */
public @Data class UserRegisterDto extends UserMobileDto {

	private static final long serialVersionUID = 1L;

	private Long mobile;
	private String smsCaptcha;
	private String password;
	private Integer origin;//android、ios
	private String code; //邀请码
	private String registrationId;

}
