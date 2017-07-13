package com.chinaxiaopu.xiaopuMobi.dto;


import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author Wang
 *
 */
public @Data class UserNotifyDto extends ContextDto{

	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private Date timePoint;//时间点，用来避免数据分页造成的数据重复出现

	public Date getTimePoint() {
		if(StringUtils.isEmpty(timePoint) && getPage()==1){
			setTimePoint(new Date());
		}
		return timePoint;
	}

	public void setTimePoint(Date timePoint) {
		this.timePoint = timePoint;
	}

}
