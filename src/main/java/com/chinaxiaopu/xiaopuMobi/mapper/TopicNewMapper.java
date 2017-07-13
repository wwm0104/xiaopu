package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.TopicNew;
import com.chinaxiaopu.xiaopuMobi.model.TopicNewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TopicNewMapper {
    long countByExample(TopicNewExample example);

    int deleteByExample(TopicNewExample example);

    int deleteByPrimaryKey(Integer topicId);

    int insert(TopicNew record);

    int insertSelective(TopicNew record);

    List<TopicNew> selectByExample(TopicNewExample example);

    TopicNew selectByPrimaryKey(Integer topicId);

    int updateByExampleSelective(@Param("record") TopicNew record, @Param("example") TopicNewExample example);

    int updateByExample(@Param("record") TopicNew record, @Param("example") TopicNewExample example);

    int updateByPrimaryKeySelective(TopicNew record);

    int updateByPrimaryKey(TopicNew record);
}