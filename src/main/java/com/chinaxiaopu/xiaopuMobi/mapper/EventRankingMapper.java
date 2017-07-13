package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.topic.EventRankDto;
import com.chinaxiaopu.xiaopuMobi.model.EventRanking;
import com.chinaxiaopu.xiaopuMobi.model.EventRankingExample;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.topic.EventRankVo;
import org.apache.ibatis.annotations.Param;

public interface EventRankingMapper {
    long countByExample(EventRankingExample example);

    int deleteByExample(EventRankingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventRanking record);

    int insertSelective(EventRanking record);

    List<EventRanking> selectByExample(EventRankingExample example);

    EventRanking selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventRanking record, @Param("example") EventRankingExample example);

    int updateByExample(@Param("record") EventRanking record, @Param("example") EventRankingExample example);

    int updateByPrimaryKeySelective(EventRanking record);

    int updateByPrimaryKey(EventRanking record);

    /**
     * 活动 日周月排行榜接口
     *
     * @param eventRankDto
     * @return
     */
    List<EventRankVo> getEventRankList(EventRankDto eventRankDto);

    /**
     * 活动 日排行 数据查询
     *
     * @return
     */
    List<EventRankVo> getEventDayList();

    /**
     * 活动日排行 老数据 修改状态
     *
     * @return
     */
    int updateOldEventDay();

    /**
     * 插入 活动 日 数据
     */
    int insertNewEventDay(List<EventRankVo> newList);

    /**
     * 删除 前天的数据
     *
     * @return
     */
    int deleteOldEventDay();

    /**
     * 活动 周排行 数据查询
     *
     * @return
     */
    List<EventRankVo> getEventWeekList();

    /**
     * 活动周排行 老数据 修改状态
     *
     * @return
     */
    int updateOldEventWeek();

    /**
     * 插入 活动 周 数据
     *
     * @param newList
     * @return
     */
    int insertNewEventWeek(List<EventRankVo> newList);

    /**
     * 删除 上周的数据
     *
     * @return
     */
    int deleteOldEventWeek();

    /**
     * 活动 月排行 数据查询
     *
     * @return
     */
    List<EventRankVo> getEventMonthList();

    /**
     * 活动月排行 老数据 修改状态
     *
     * @return
     */
    int updateOldEventMonth();

    /**
     * 插入 活动 月 数据
     *
     * @param newList
     * @return
     */
    int insertNewEventMonth(List<EventRankVo> newList);

    /**
     * 删除 上月的数据
     *
     * @return
     */
    int deleteOldEventMonth();
}