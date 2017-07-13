package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.EventLotteryUser;
import com.chinaxiaopu.xiaopuMobi.model.EventLotteryUserExample;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.eventLottery.EventLotteryUserVo;

import com.chinaxiaopu.xiaopuMobi.vo.eventLottery.LotteryUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface EventLotteryUserMapper {
    long countByExample(EventLotteryUserExample example);

    int deleteByExample(EventLotteryUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventLotteryUser record);

    int insertSelective(EventLotteryUser record);

    List<EventLotteryUser> selectByExample(EventLotteryUserExample example);

    EventLotteryUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventLotteryUser record, @Param("example") EventLotteryUserExample example);

    int updateByExample(@Param("record") EventLotteryUser record, @Param("example") EventLotteryUserExample example);

    int updateByPrimaryKeySelective(EventLotteryUser record);

    int updateByPrimaryKey(EventLotteryUser record);

    /**
     * 查询最大轮数的数据
     *
     * @param eventId
     * @return
     */
    EventLotteryUser selectEventLotteryUserByEventId(@Param("eventId") Integer eventId);

    /**
     * 查询所有的获奖用户
     *
     * @param eventId
     * @param round
     * @return
     */
    List<EventLotteryUserVo> selectAllWinerUser(@Param("eventId") Integer eventId, @Param("round") Integer round);

    /**
     * 查询所有的未抽奖用户
     *
     * @param eventId
     * @return
     */
    List<EventLotteryUserVo> selectAllUserList(Integer eventId);

    //根据活动id轮次id查询获奖用户集
    List<LotteryUserVo> selectUserByEventIdAndRound(@Param("eventId") Integer eventId,@Param("round") Integer round);
    @Select("select round from event_lottery_user where event_id=#{eventId} GROUP BY round")
    List<Integer> selectRoundByEventId(@Param("eventId") Integer eventId);  //根据活动id查询轮次
}