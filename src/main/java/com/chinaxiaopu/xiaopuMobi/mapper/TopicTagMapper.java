package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.TopicTag;
import com.chinaxiaopu.xiaopuMobi.model.TopicTagExample;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.topic.TopicTagsVo;
import org.apache.ibatis.annotations.Param;

public interface TopicTagMapper {
    long countByExample(TopicTagExample example);

    int deleteByExample(TopicTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TopicTag record);

    int insertSelective(TopicTag record);

    List<TopicTag> selectByExample(TopicTagExample example);

    TopicTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TopicTag record, @Param("example") TopicTagExample example);

    int updateByExample(@Param("record") TopicTag record, @Param("example") TopicTagExample example);

    int updateByPrimaryKeySelective(TopicTag record);

    int updateByPrimaryKey(TopicTag record);

    /**
     * 主题关联的社团
     *
     * @return
     */
    List<TopicTagsVo> getTagsGroupList();
    /**
     * 主题关联的活动
     *
     * @return
     */
    List<TopicTagsVo> getTagsEventList();

    List<TopicTagsVo> getGroupTagsByTopicId(Integer topicId);
    List<TopicTagsVo> getEventTagsByTopicId(Integer topicId);

    String  selectEventNameById(Integer id);
    String  selectGroupNameById(Integer id);

    TopicTag selectTargetNameByPrizeLogId(@Param("id") Integer id);
}