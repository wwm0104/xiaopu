package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.PrizeImprest;
import com.chinaxiaopu.xiaopuMobi.model.PrizeImprestExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PrizeImprestMapper {
    long countByExample(PrizeImprestExample example);

    int deleteByExample(PrizeImprestExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PrizeImprest record);

    int insertSelective(PrizeImprest record);

    List<PrizeImprest> selectByExample(PrizeImprestExample example);

    PrizeImprest selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PrizeImprest record, @Param("example") PrizeImprestExample example);

    int updateByExample(@Param("record") PrizeImprest record, @Param("example") PrizeImprestExample example);

    int updateByPrimaryKeySelective(PrizeImprest record);

    int updateByPrimaryKey(PrizeImprest record);

    int selectCountByPkId(Integer pkId);

    int delectImprestWhenTurnDown(Map<String,Object> map);
}