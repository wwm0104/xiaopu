package com.chinaxiaopu.xiaopuMobi.mapper;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.dto.EventDto;
import com.chinaxiaopu.xiaopuMobi.model.*;

/**
 * 活动后台管理
 * @author ycy
 *
 */
public interface BackStageManagementEventMapper {

	/**
	 * 活动列表查询
	 * @return
	 */
	List<EventDto> selectEventList(EventDto eventDto);

	EventDto selectEventDteils(EventDto eventDto);

	/**
	 * 查询可参与的社团
	 * @return
	 */
	List<EventGroup> selectGroupByEventId(EventGroup eventGroup);

	Group selectInfoByGroupId(Group group);

	Long selectGroupCount();

	Long selectEventCount();

	Long selectAllEventNum();

	Long selectAllGroupNum();

	int updateEventById(Event event);

	int updateEventMembers(EventMember eventMember);

	List<EventDto> selectIndexEvent();

	List<Group> selectIndexGroup();

	List<EventMember> selectEventMember(EventMember eventMember);
}
