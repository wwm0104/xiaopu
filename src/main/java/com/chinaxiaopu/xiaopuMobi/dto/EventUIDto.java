package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * 应用在：
 * 	活动列表
 * 	活动详情
 * @author Wang
 *
 */
public @Data class EventUIDto extends ContextDto {

	private Integer eventId;//活动Id
	private Integer userId;//用户Id

}
