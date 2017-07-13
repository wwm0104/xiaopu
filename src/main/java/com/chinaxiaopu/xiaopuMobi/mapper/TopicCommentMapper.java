package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.TopicComment;
import com.chinaxiaopu.xiaopuMobi.model.TopicCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TopicCommentMapper {
    long countByExample(TopicCommentExample example);

    int deleteByExample(TopicCommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TopicComment record);

    int insertSelective(TopicComment record);

    List<TopicComment> selectByExample(TopicCommentExample example);

    TopicComment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TopicComment record, @Param("example") TopicCommentExample example);

    int updateByExample(@Param("record") TopicComment record, @Param("example") TopicCommentExample example);

    int updateByPrimaryKeySelective(TopicComment record);

    int updateByPrimaryKey(TopicComment record);

    List<TopicComment> selectByTopicId(@Param("topicId") Integer topicId);
    @Select("select count(1) from topic_comment where topic_id=#{topicId}")
    int selectCntByTopicId(@Param("topicId") int topicId);
}