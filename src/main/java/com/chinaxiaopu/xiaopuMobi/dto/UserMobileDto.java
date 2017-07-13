package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * 
 * @author Wang
 *
 */
public @Data class UserMobileDto extends ContextDto{

	private static final long serialVersionUID = 1L;

	private Integer userId;
	private Long mobile;
	private String smsCaptcha;//验证码

}
