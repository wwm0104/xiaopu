package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.topic.UserRankDto;
import com.chinaxiaopu.xiaopuMobi.model.UserRanking;
import com.chinaxiaopu.xiaopuMobi.model.UserRankingExample;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.topic.UserRankVo;
import org.apache.ibatis.annotations.Param;

public interface UserRankingMapper {
    long countByExample(UserRankingExample example);

    int deleteByExample(UserRankingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRanking record);

    int insertSelective(UserRanking record);

    List<UserRanking> selectByExample(UserRankingExample example);

    UserRanking selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserRanking record, @Param("example") UserRankingExample example);

    int updateByExample(@Param("record") UserRanking record, @Param("example") UserRankingExample example);

    int updateByPrimaryKeySelective(UserRanking record);

    int updateByPrimaryKey(UserRanking record);

    /**
     * 不服榜 /达人榜
     *
     * @param userRankDto
     * @return
     */
    List<UserRankVo> getUserRankList(UserRankDto userRankDto);

    /**
     * 定时器 ：不服榜　查询用户 挑战的次数
     *
     * @return
     */

    List<UserRankVo> getUserBufuList();

    /**
     * 定时器 ：不服榜　修改老数据的状态
     *
     * @return
     */
    int updateUserBu();

    /**
     * 定时器 ： 不服榜  批量添加 新数据
     *
     * @param newList
     * @return
     */
    int insertUserBu(List<UserRankVo> newList);

    /**
     * 定时器 ： 不服榜  删除前天的数据
     *
     * @return
     */
    int deleteUserBu();

    /**
     * 定时器 ： 达人榜 查询获胜次数最多的用户
     *
     * @return
     */
    List<UserRankVo> getUserDarenList();

    /**
     * 定时器 ： 达人榜  修改达人榜 老数据
     */
    int updateUserDaren();

    /**
     * 定时器 ： 达人榜 批量添加 新数据
     *
     * @param newList
     * @return
     */
    int insertUserDaren(List<UserRankVo> newList);

    /**
     * 定时器 ： 达人榜  删除前天的数据
     *
     * @return
     */
    int deleteUserDaren();
}