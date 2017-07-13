package com.chinaxiaopu.xiaopuMobi.dto;

import com.chinaxiaopu.xiaopuMobi.model.Event;
import com.chinaxiaopu.xiaopuMobi.model.Group;
import lombok.Data;

/**
 * Created by Wang on 2016/12/08.
 */

@Data
public class ReviewedEventDto extends Event{

	private String memo;
	private Integer userId;
	
}
