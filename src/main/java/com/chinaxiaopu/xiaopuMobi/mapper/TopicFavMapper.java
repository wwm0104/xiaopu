package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.MyTopicParam;
import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.chinaxiaopu.xiaopuMobi.model.TopicFav;
import com.chinaxiaopu.xiaopuMobi.model.TopicFavExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TopicFavMapper {
    long countByExample(TopicFavExample example);

    int deleteByExample(TopicFavExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TopicFav record);

    int insertSelective(TopicFav record);

    List<TopicFav> selectByExample(TopicFavExample example);

    TopicFav selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TopicFav record, @Param("example") TopicFavExample example);

    int updateByExample(@Param("record") TopicFav record, @Param("example") TopicFavExample example);

    int updateByPrimaryKeySelective(TopicFav record);

    int updateByPrimaryKey(TopicFav record);

    int selectCountByTopicIdAndUserId(@Param("topicId")Integer topicId, @Param("userId")Integer userId);

    int deleteByTopicIdAndUserId(@Param("topicId")Integer topicId, @Param("userId")Integer userId);

    List<Topic> selectUserFavTopic(MyTopicParam myTopicParam);

    List<Integer> selectTopicIdListByUserId(Integer userId);

    @Select("select count(1) from topic_fav where user_id=#{userId,jdbcType=INTEGER}")
    int selectCountByUserId(@Param("userId") Integer userId);   //查询收藏总数
}