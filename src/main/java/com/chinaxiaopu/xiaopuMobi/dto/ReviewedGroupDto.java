package com.chinaxiaopu.xiaopuMobi.dto;

import com.chinaxiaopu.xiaopuMobi.model.Group;
import lombok.Data;

/**
 * Created by Wang on 2016/12/08.
 */

@Data
public class ReviewedGroupDto extends Group{

	private String memo;
	private Integer userId;
	
}
