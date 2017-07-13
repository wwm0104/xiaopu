package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.TopicHot;
import com.chinaxiaopu.xiaopuMobi.model.TopicHotExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TopicHotMapper {
    long countByExample(TopicHotExample example);

    int deleteByExample(TopicHotExample example);

    int deleteByPrimaryKey(Integer topicId);

    int insert(TopicHot record);

    int insertSelective(TopicHot record);

    List<TopicHot> selectByExample(TopicHotExample example);

    TopicHot selectByPrimaryKey(Integer topicId);

    int updateByExampleSelective(@Param("record") TopicHot record, @Param("example") TopicHotExample example);

    int updateByExample(@Param("record") TopicHot record, @Param("example") TopicHotExample example);

    int updateByPrimaryKeySelective(TopicHot record);

    int updateByPrimaryKey(TopicHot record);
}