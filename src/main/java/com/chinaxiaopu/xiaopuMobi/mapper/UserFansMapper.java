package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.UserFansTopicLisDto;
import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.chinaxiaopu.xiaopuMobi.model.UserFans;
import com.chinaxiaopu.xiaopuMobi.model.UserFansExample;
import com.chinaxiaopu.xiaopuMobi.model.UserFansKey;
import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.topic.UserFanListVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserFansMapper {
    long countByExample(UserFansExample example);

    int deleteByExample(UserFansExample example);

    int deleteByPrimaryKey(UserFansKey key);

    int insert(UserFans record);

    int insertSelective(UserFans record);

    List<UserFans> selectByExample(UserFansExample example);

    UserFans selectByPrimaryKey(UserFansKey key);

    int updateByExampleSelective(@Param("record") UserFans record, @Param("example") UserFansExample example);

    int updateByExample(@Param("record") UserFans record, @Param("example") UserFansExample example);

    int updateByPrimaryKeySelective(UserFans record);

    int updateByPrimaryKey(UserFans record);
    @Select("SELECT count(1) FROM user_fans WHERE user_id = #{userId,jdbcType=INTEGER}")
    int selectByUserId(@Param("userId") Integer userId);  //查询粉丝总数

    @Select("SELECT count(1) FROM user_fans WHERE fans_id = #{fansId,jdbcType=INTEGER}")
    int selectByFanId(@Param("fansId") Integer fansId);   //查询关注总数

    List<UserFanListVo> getFollowListByUserId(@Param("userId") Integer userId);    //查询关注列表

    List<UserFanListVo> getFanListByUserId(@Param("userId") Integer userId);       //查询粉丝列表



    List<Topic> selectFollowTopics(UserFansTopicLisDto userFansTopicLisDto);

    int selectIfFollow(UserFans userFans);

    int updateIsFocus(UserFans userFans);

    int removeFocus(UserFans userFans);
}