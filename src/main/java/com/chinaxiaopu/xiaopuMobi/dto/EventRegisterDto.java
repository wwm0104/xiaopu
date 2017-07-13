package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * 应用在：
 * 	活动列表
 * 	活动详情
 * @author Wang
 *
 */
public @Data class EventRegisterDto extends ContextDto {

	private Integer eventId;//活动Id
	private String eventName;//活动名称
	private Integer userId;//用户Id
	private Integer groupId;//社团Id
	private Integer isFollow;//是否关注，1关注，0未关注

}
