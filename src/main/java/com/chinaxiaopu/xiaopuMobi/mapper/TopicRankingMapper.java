package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.topic.VotesRankDto;
import com.chinaxiaopu.xiaopuMobi.model.TopicRanking;
import com.chinaxiaopu.xiaopuMobi.model.TopicRankingExample;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.topic.VotesRankVo;
import org.apache.ibatis.annotations.Param;

public interface TopicRankingMapper {
    long countByExample(TopicRankingExample example);

    int deleteByExample(TopicRankingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TopicRanking record);

    int insertSelective(TopicRanking record);

    List<TopicRanking> selectByExample(TopicRankingExample example);

    TopicRanking selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TopicRanking record, @Param("example") TopicRankingExample example);

    int updateByExample(@Param("record") TopicRanking record, @Param("example") TopicRankingExample example);

    int updateByPrimaryKeySelective(TopicRanking record);

    int updateByPrimaryKey(TopicRanking record);

    /**
     * 投票排行榜日，月，周榜
     *
     * @param votesRankDto
     * @return
     */
    List<VotesRankVo> getVotesRankList(VotesRankDto votesRankDto);

    /**
     * 定时器 ： 投票 日排行 新数据查询
     *
     * @return
     */
    List<VotesRankVo> getVotesDayList();

    /**
     * 定时器 ： 投票 日排行  修改老数据状态为 0
     *
     * @return
     */
    int updateOldVotesDay();

    /**
     * 定时器 ： 投票 日排行 批量添加 新数据
     *
     * @param newList
     */
    int insertNewVotesDay(List<VotesRankVo> newList);

    /**
     * 定时器 ： 投票 日排行 删除前天的数据
     *
     * @return
     */
    int deleteOldVotesDay();

    /**
     * 定时器 ： 投票 月排行 新数据查询
     *
     * @return
     */
    List<VotesRankVo> getVotesMonthList();

    /**
     * 定时器 ： 投票 月排行  修改老数据状态为 0
     *
     * @return
     */
    int updateOldVotesMonth();

    /**
     * 定时器 ： 投票 月排行 批量添加 新数据
     *
     * @param newList
     */
    int insertNewVotesMonth(List<VotesRankVo> newList);

    /**
     * 定时器 ： 投票 月排行 删除上月的数据
     *
     * @return
     */
    int deleteOldVotesMonth();

    /**
     * 定时器 ： 投票 周排行 新数据查询
     *
     * @return
     */
    List<VotesRankVo> getVotesWeekList();

    /**
     * 定时器 ： 投票 周排行  修改老数据状态为 0
     */
    int updateOldVotesWeek();

    /**
     * 定时器 ： 投票 周排行 批量添加 新数据
     *
     * @param newList
     * @return
     */
    int insertNewVotesWeek(List<VotesRankVo> newList);

    /**
     * 定时器 ： 投票 周排行 删除上周的数据
     *
     * @return
     */
    int deleteOldVotesWeek();

    /**
     * 定时器 ： 投票汇总  查询最新数据
     *
     * @return
     */
    List<VotesRankVo> getVotesPoolList();

    /**
     * 定时器 ： 投票汇总  更新老数据状态
     *
     * @return
     */
    int updateVotesPool();

    /**
     * 定时器 ： 投票汇总 批量添加
     *
     * @param newList
     * @return
     */
    int insertVotesPool(List<VotesRankVo> newList);

    /**
     * 定时器  ： 投票汇总  删除两天前的数据
     *
     * @return
     */
    int deleteVotesPool();
}