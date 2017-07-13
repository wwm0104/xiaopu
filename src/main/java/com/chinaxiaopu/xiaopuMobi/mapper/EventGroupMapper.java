package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicGroupDto;
import com.chinaxiaopu.xiaopuMobi.model.Event;
import com.chinaxiaopu.xiaopuMobi.model.EventGroup;
import com.chinaxiaopu.xiaopuMobi.model.EventGroupExample;
import com.chinaxiaopu.xiaopuMobi.model.EventGroupKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;


public interface EventGroupMapper {
    long countByExample(EventGroupExample example);

    int deleteByExample(EventGroupExample example);

    int deleteByPrimaryKey(EventGroupKey key);

    int insert(EventGroup record);

    int insertSelective(EventGroup record);

    List<EventGroup> selectByExample(EventGroupExample example);

    EventGroup selectByPrimaryKey(EventGroupKey key);

    int updateByExampleSelective(@Param("record") EventGroup record, @Param("example") EventGroupExample example);

    int updateByExample(@Param("record") EventGroup record, @Param("example") EventGroupExample example);

    int updateByPrimaryKeySelective(EventGroup record);

    int updateByPrimaryKey(EventGroup record);

    List<EventGroup> selectByEventId(Integer eventId);

    List<Event> selectEventByGroupId(TopicGroupDto topicGroupDto);
}