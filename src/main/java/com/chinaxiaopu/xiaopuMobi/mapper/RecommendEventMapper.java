package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.RecommendEvent;
import com.chinaxiaopu.xiaopuMobi.model.RecommendEventExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecommendEventMapper {
    long countByExample(RecommendEventExample example);

    int deleteByExample(RecommendEventExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RecommendEvent record);

    int insertSelective(RecommendEvent record);

    List<RecommendEvent> selectByExample(RecommendEventExample example);

    RecommendEvent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RecommendEvent record, @Param("example") RecommendEventExample example);

    int updateByExample(@Param("record") RecommendEvent record, @Param("example") RecommendEventExample example);

    int updateByPrimaryKeySelective(RecommendEvent record);

    int updateByPrimaryKey(RecommendEvent record);

    List<RecommendEvent> hotEvent();

    RecommendEvent selectByEventId(int eventId);

    int selectMaxSort();

    Integer isTop(Integer eventId);
}