package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.TopicPlay;
import com.chinaxiaopu.xiaopuMobi.model.TopicPlayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface TopicPlayMapper {
    long countByExample(TopicPlayExample example);

    int deleteByExample(TopicPlayExample example);

    int deleteByPrimaryKey(Integer topicId);

    int insert(TopicPlay record);

    int insertSelective(TopicPlay record);

    List<TopicPlay> selectByExample(TopicPlayExample example);

    TopicPlay selectByPrimaryKey(Integer topicId);

    int updateByExampleSelective(@Param("record") TopicPlay record, @Param("example") TopicPlayExample example);

    int updateByExample(@Param("record") TopicPlay record, @Param("example") TopicPlayExample example);

    int updateByPrimaryKeySelective(TopicPlay record);

    int updateByPrimaryKey(TopicPlay record);

    /**
     * 点击增加播放量
     * @param topicId
     * @return
     */
    @Update("INSERT INTO topic_play VALUES(#{topicId},1,NOW()) ON DUPLICATE KEY UPDATE play_cnt=play_cnt+1")
    int updatePlayCntByTopicId(@Param("topicId") final Integer topicId);
}