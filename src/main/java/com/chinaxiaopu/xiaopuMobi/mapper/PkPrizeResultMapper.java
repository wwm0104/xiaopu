package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeTakeDto;
import com.chinaxiaopu.xiaopuMobi.model.PkPrizeResult;
import com.chinaxiaopu.xiaopuMobi.model.PkPrizeResultExample;
import com.chinaxiaopu.xiaopuMobi.model.PkPrizeResultKey;
import java.util.List;
import java.util.Map;

import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizeViewVo;
import org.apache.ibatis.annotations.Param;

public interface PkPrizeResultMapper {
    long countByExample(PkPrizeResultExample example);

    int deleteByExample(PkPrizeResultExample example);

    int deleteByPrimaryKey(PkPrizeResultKey key);

    int insert(PkPrizeResult record);

    int insertSelective(PkPrizeResult record);

    List<PkPrizeResult> selectByExample(PkPrizeResultExample example);

    PkPrizeResult selectByPrimaryKey(PkPrizeResultKey key);

    int updateByExampleSelective(@Param("record") PkPrizeResult record, @Param("example") PkPrizeResultExample example);

    int updateByExample(@Param("record") PkPrizeResult record, @Param("example") PkPrizeResultExample example);

    int updateByPrimaryKeySelective(PkPrizeResult record);

    int updateByPrimaryKey(PkPrizeResult record);

    PrizeViewVo selectByPrizeIdAndUserId(@Param("prizeId")Integer prizeId, @Param("userId")Integer userId,@Param("topicId")Integer topicId);

    List<PrizeViewVo> selectByUserId(@Param("userId")Integer userId,@Param("type")Integer type);

    int updatePrizeTake(PrizeTakeDto prizeTakeDto);

    int updateCodeByPkId(@Param("pkId")Integer pkId, @Param("code")String code);

    int checkIsTake(Map<String,Object> map);
}