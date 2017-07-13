package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.UserChannelSubscribe;
import com.chinaxiaopu.xiaopuMobi.model.UserChannelSubscribeExample;

import java.util.Date;
import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.audio.NewAudioVo;
import com.chinaxiaopu.xiaopuMobi.vo.audio.SubscribeVo;
import org.apache.ibatis.annotations.Param;

public interface UserChannelSubscribeMapper {
    long countByExample(UserChannelSubscribeExample example);

    int deleteByExample(UserChannelSubscribeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserChannelSubscribe record);

    int insertSelective(UserChannelSubscribe record);

    List<UserChannelSubscribe> selectByExample(UserChannelSubscribeExample example);

    UserChannelSubscribe selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserChannelSubscribe record, @Param("example") UserChannelSubscribeExample example);

    int updateByExample(@Param("record") UserChannelSubscribe record, @Param("example") UserChannelSubscribeExample example);

    int updateByPrimaryKeySelective(UserChannelSubscribe record);

    int updateByPrimaryKey(UserChannelSubscribe record);

    /**订阅的频道列表*/
    List<SubscribeVo> getSubscribeChannelList(@Param("userId")Integer userId, @Param("timePoint")Date timePoint);

    /**频道的最新音频*/
    NewAudioVo getNewAudio(Integer channelId);

}