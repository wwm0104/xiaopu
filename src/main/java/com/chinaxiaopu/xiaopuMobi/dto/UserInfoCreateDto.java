package com.chinaxiaopu.xiaopuMobi.dto;


import lombok.Data;

/**
 *
 * @author Wang
 *
 */
public @Data class UserInfoCreateDto extends ContextDto {

	private static final long serialVersionUID = 1L;

	private Integer userId;//用户Id，用户信息编辑时使用
	private Integer schoolId;
	private String schoolName;
	private String name;//用户姓名
	private String studentNo;//学号
	private String smsCaptcha;//验证码
	private Long mobile;//手机号
	private String avatarUrl;//头像
	private String qq;//QQ
	private Integer origin;//android、ios
	private Integer userSex;//性别

}