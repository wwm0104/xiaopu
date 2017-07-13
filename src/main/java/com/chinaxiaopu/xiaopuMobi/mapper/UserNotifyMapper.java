package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.model.UserNotify;
import com.chinaxiaopu.xiaopuMobi.model.UserNotifyExample;

import java.util.Date;
import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.UserNotifyVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserNotifyMapper {
    long countByExample(UserNotifyExample example);

    int deleteByExample(UserNotifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserNotify record);

    int insertSelective(UserNotify record);

    List<UserNotify> selectByExample(UserNotifyExample example);

    UserNotify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserNotify record, @Param("example") UserNotifyExample example);

    int updateByExample(@Param("record") UserNotify record, @Param("example") UserNotifyExample example);

    int updateByPrimaryKeySelective(UserNotify record);

    int updateByPrimaryKey(UserNotify record);

    List<UserNotifyVo> selectByUser(@Param("userId")Integer userId, @Param("timePoint")Date timePoint);

    int notifyRead(@Param("userId") final Integer userId);
}