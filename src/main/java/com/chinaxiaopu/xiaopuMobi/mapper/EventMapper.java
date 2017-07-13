package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.partner.PartnerEventDto;
import com.chinaxiaopu.xiaopuMobi.model.Event;
import com.chinaxiaopu.xiaopuMobi.model.EventExample;
import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.EventMemberVo;
import com.chinaxiaopu.xiaopuMobi.vo.president.EventVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.EventPhotoVo;
import org.apache.ibatis.annotations.Param;

public interface EventMapper {
    long countByExample(EventExample example);

    int deleteByExample(EventExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Event record);

    int insertSelective(Event record);

    List<Event> selectByExample(EventExample example);

    Event selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Event record, @Param("example") EventExample example);

    int updateByExample(@Param("record") Event record, @Param("example") EventExample example);

    int updateByPrimaryKeySelective(Event record);

    int updateByPrimaryKey(Event record);

	List<Event> getMyEventListByUserId(@Param("userId")Integer userId,@Param("status")Integer status);

	List<Event> getConcernEventListByUserId(Integer userId);

	List<Event> getEventListByEvent(Event event);

    List<Event> getEventListByEventAndSchoolId(@Param("event")Event event, @Param("schoolId")Integer schoolId);

    List<Event> getGroupEventList(@Param("event")Event event, @Param("timePoint")Integer timePoint, @Param("userId")Integer userId);

    List<EventVo> selectByGroupId(PartnerEventDto partnerEventDto);  //根据社团id查询所有活动，根据活动名称模糊查询，根据任务状态查询

    List<Event> selectAllEvent();

    List<EventPhotoVo> photos(Integer eventId);

    int isPresidentByEventIdAndUserId(@Param("eventId")Integer eventId, @Param("userId")Integer userId);

    List<EventMemberVo> getEventListByUserId(Integer userId);
}