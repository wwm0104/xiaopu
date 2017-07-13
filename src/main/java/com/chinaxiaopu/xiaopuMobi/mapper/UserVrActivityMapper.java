package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.UserVrActivity;
import com.chinaxiaopu.xiaopuMobi.model.UserVrActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserVrActivityMapper {
    long countByExample(UserVrActivityExample example);

    int deleteByExample(UserVrActivityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserVrActivity record);

    int insertSelective(UserVrActivity record);

    List<UserVrActivity> selectByExample(UserVrActivityExample example);

    UserVrActivity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserVrActivity record, @Param("example") UserVrActivityExample example);

    int updateByExample(@Param("record") UserVrActivity record, @Param("example") UserVrActivityExample example);

    int updateByPrimaryKeySelective(UserVrActivity record);

    int updateByPrimaryKey(UserVrActivity record);

    @Select("select IFNULL(sum(activity_cnt),0) from user_vr_activitys where user_id=#{userId}")
    int selectSumCntByUserId(@Param("userId") final Integer userId);
}