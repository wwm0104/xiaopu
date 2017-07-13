package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.topic.PKResultDetailDto;
import com.chinaxiaopu.xiaopuMobi.model.PkResult;
import com.chinaxiaopu.xiaopuMobi.model.PkResultExample;
import com.chinaxiaopu.xiaopuMobi.model.PkResultKey;

import java.util.List;
import java.util.Map;

import com.chinaxiaopu.xiaopuMobi.vo.topic.PKResultListVo;
import org.apache.ibatis.annotations.Param;

public interface PkResultMapper {
    long countByExample(PkResultExample example);

    int deleteByExample(PkResultExample example);

    int deleteByPrimaryKey(PkResultKey key);

    int insert(PkResult record);

    int insertSelective(PkResult record);

    List<PkResult> selectByExample(PkResultExample example);

    PkResult selectByPrimaryKey(PkResultKey key);

    int updateByExampleSelective(@Param("record") PkResult record, @Param("example") PkResultExample example);

    int updateByExample(@Param("record") PkResult record, @Param("example") PkResultExample example);

    int updateByPrimaryKeySelective(PkResult record);

    int updateByPrimaryKey(PkResult record);

    /**
     * 获取Pk结果list数据
     *
     * @param pKResultDto
     * @return
     */
    List<PKResultListVo> getPkResultList(PKResultDetailDto pKResultDto);

    Map<String,Object> selectByPrizeLogId(@Param("id") Integer id);//根据发奖记录id查询所有投票数



}