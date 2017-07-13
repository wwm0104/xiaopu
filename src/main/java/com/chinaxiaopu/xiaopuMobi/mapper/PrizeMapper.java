package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.MyTopicParam;
import com.chinaxiaopu.xiaopuMobi.dto.UserInfoListDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.PrizesDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeListDto;
import com.chinaxiaopu.xiaopuMobi.model.Prize;
import com.chinaxiaopu.xiaopuMobi.model.PrizeExample;
import com.chinaxiaopu.xiaopuMobi.model.PrizeKey;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.PrizesVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizeVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizesListVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizesRecommVo;
import org.apache.ibatis.annotations.Param;

public interface PrizeMapper {
    long countByExample(PrizeExample example);

    int deleteByExample(PrizeExample example);

    int deleteByPrimaryKey(PrizeKey key);

    int insert(Prize record);

    int insertSelective(Prize record);

    List<Prize> selectByExample(PrizeExample example);

    Prize selectByPrimaryKey(PrizeKey key);

    int updateByExampleSelective(@Param("record") Prize record, @Param("example") PrizeExample example);

    int updateByExample(@Param("record") Prize record, @Param("example") PrizeExample example);

    int updateByPrimaryKeySelective(Prize record);

    int updateByPrimaryKey(Prize record);

    List<Prize> selectAllPrizesType(MyTopicParam myTopicParam);

    List<Prize> selectPrizesNameByType(Prize prize);

    List<Prize> selectPrizesNameByTypeForOffical(Prize prize);

    int updateStockById(Integer prizeId);

    Prize selectPrizeById(Integer prizeId);

    List<PrizeVo> selectPrizeList(@Param("type") Integer type, @Param("status") Integer status, @Param("isPublic") Integer isPublic, @Param("sort") Integer sort);

    PrizeVo selectByPrizeId(int id);

    List<Prize> prizeScan();

    int giveBackStock(Integer prizeId);

    /**
     * 武宁
     * 首页奖品推荐
     *
     * @return
     */
    List<PrizesRecommVo> recommentPrizes();

    /**
     * 查询奖品列表
     *
     * @param prizeListDto
     * @return
     */
    List<PrizesListVo> selectAllPrizeAndPkCount(PrizeListDto prizeListDto);

    /**
     * 根据礼物id查询获奖人信息
     *
     * @param
     * @return
     */
    List<UserInfoListDto> selectRwardUserByPrizeId(Integer prizeId);
}