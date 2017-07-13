package com.chinaxiaopu.xiaopuMobi.dto;


import lombok.Data;

/**
 * 
 * @author Wang
 *
 */
public @Data class UserUploadDto extends ContextDto{

	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String nickName;//昵称
	private String avatarUrl;//头像

}
