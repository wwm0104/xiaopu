package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.EventUserMsg;
import com.chinaxiaopu.xiaopuMobi.model.EventUserMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EventUserMsgMapper {
    long countByExample(EventUserMsgExample example);

    int deleteByExample(EventUserMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventUserMsg record);

    int insertSelective(EventUserMsg record);

    List<EventUserMsg> selectByExample(EventUserMsgExample example);

    EventUserMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventUserMsg record, @Param("example") EventUserMsgExample example);

    int updateByExample(@Param("record") EventUserMsg record, @Param("example") EventUserMsgExample example);

    int updateByPrimaryKeySelective(EventUserMsg record);

    int updateByPrimaryKey(EventUserMsg record);
}