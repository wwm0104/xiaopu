package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.TopicLike;
import com.chinaxiaopu.xiaopuMobi.model.TopicLikeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TopicLikeMapper {
    long countByExample(TopicLikeExample example);

    int deleteByExample(TopicLikeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TopicLike record);

    int insertSelective(TopicLike record);

    List<TopicLike> selectByExample(TopicLikeExample example);

    TopicLike selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TopicLike record, @Param("example") TopicLikeExample example);

    int updateByExample(@Param("record") TopicLike record, @Param("example") TopicLikeExample example);

    int updateByPrimaryKeySelective(TopicLike record);

    int updateByPrimaryKey(TopicLike record);

    int selectCountByTopicIdAndUserId(@Param("topicId") Integer topicId, @Param("userId") Integer userId);

    List<Integer> selectTopicIdListByUserId(Integer userId);
}