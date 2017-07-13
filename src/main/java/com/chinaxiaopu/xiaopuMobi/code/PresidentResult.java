package com.chinaxiaopu.xiaopuMobi.code;


import lombok.Data;


/**
 * 系统登录接口返回json内容类
 * Created by liuwei
 * date: 16/5/27
 */
public @Data class PresidentResult extends AbstractResult {

	private static final long serialVersionUID = 1L;
	
	private Integer resultCode;
	private Integer eventApplyCount;
	private Integer groupApplyCount;
	private String msg;
}
