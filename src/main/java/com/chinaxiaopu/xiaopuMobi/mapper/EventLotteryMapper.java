package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.EventLottery;
import com.chinaxiaopu.xiaopuMobi.model.EventLotteryExample;
import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.eventLottery.EventLotteryVo;
import org.apache.ibatis.annotations.Param;

public interface EventLotteryMapper {
    long countByExample(EventLotteryExample example);

    int deleteByExample(EventLotteryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventLottery record);

    int insertSelective(EventLottery record);

    List<EventLottery> selectByExample(EventLotteryExample example);

    EventLottery selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventLottery record, @Param("example") EventLotteryExample example);

    int updateByExample(@Param("record") EventLottery record, @Param("example") EventLotteryExample example);

    int updateByPrimaryKeySelective(EventLottery record);

    int updateByPrimaryKey(EventLottery record);

    List<EventLotteryVo> getEventLotteryList();
}