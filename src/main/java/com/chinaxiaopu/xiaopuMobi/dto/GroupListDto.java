package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * 应用在：
 *  社团列表
 *  社团信息
 *
 * @author Wang
 *
 */
public @Data class GroupListDto extends ContextDto {

	private static final long serialVersionUID = 1L;

	private String groupName;
	private String categoryId;//社团类型Id
	private Integer groupStatus;//社团状态
	private Integer schoolId;

}
