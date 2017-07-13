package com.chinaxiaopu.xiaopuMobi.mapper;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.model.President;

public interface PresidentMapper {
   
	/**
	 * 根据条件查询社长
	 * ycy
	 * @return
	 */
	List<President> selectPresident(President president);

	List<President> selectPresidentBySchool(President president);
}
