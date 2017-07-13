package com.chinaxiaopu.xiaopuMobi.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by liuwei
 * date: 16/9/28
 */
public @Data class AuthUserDto implements Serializable {
	
	private String accessToken;
    private Integer userId;
    private String openid;
}
