package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.UserFocusAnchor;
import com.chinaxiaopu.xiaopuMobi.model.UserFocusAnchorExample;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.vo.UserAduioVo;
import com.chinaxiaopu.xiaopuMobi.vo.UserFocusVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserFocusAnchorMapper {
    long countByExample(UserFocusAnchorExample example);

    int deleteByExample(UserFocusAnchorExample example);

    int deleteByPrimaryKey(Integer id);

    int deleteByUserFocusAbchor(UserFocusAnchor userFocusAnchor);

    int insert(UserFocusAnchor record);

    int insertSelective(UserFocusAnchor record);

    List<UserFocusAnchor> selectByExample(UserFocusAnchorExample example);

    UserFocusAnchor selectByPrimaryKey(Integer id);

    List<UserFocusVo> selectByUserId(Integer userId);

    List<UserInfo> selectAnchorInfoByUserId(Integer userId);

    @Select("SELECT * FROM user_focus_anchor WHERE user_id = #{userId,jdbcType=INTEGER} and focus_user_id=#{focusUserId,jdbcType=INTEGER}")
    UserFocusAnchor selectByUserIdAndFocusUserId(@Param("userId") Integer userId, @Param("focusUserId") Integer focusUserId);

    int updateByExampleSelective(@Param("record") UserFocusAnchor record, @Param("example") UserFocusAnchorExample example);

    int updateByExample(@Param("record") UserFocusAnchor record, @Param("example") UserFocusAnchorExample example);

    int updateByPrimaryKeySelective(UserFocusAnchor record);

    int updateByPrimaryKey(UserFocusAnchor record);
}