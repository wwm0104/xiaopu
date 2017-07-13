package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.PrizeTakeLogDto;
import com.chinaxiaopu.xiaopuMobi.model.PkPrizeTakeLog;
import com.chinaxiaopu.xiaopuMobi.model.PkPrizeTakeLogExample;
import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.PrizeTakeLogVo;
import org.apache.ibatis.annotations.Param;

public interface PkPrizeTakeLogMapper {
    long countByExample(PkPrizeTakeLogExample example);

    int deleteByExample(PkPrizeTakeLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PkPrizeTakeLog record);

    int insertSelective(PkPrizeTakeLog record);

    List<PkPrizeTakeLog> selectByExample(PkPrizeTakeLogExample example);

    PkPrizeTakeLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PkPrizeTakeLog record, @Param("example") PkPrizeTakeLogExample example);

    int updateByExample(@Param("record") PkPrizeTakeLog record, @Param("example") PkPrizeTakeLogExample example);

    int updateByPrimaryKeySelective(PkPrizeTakeLog record);

    int updateByPrimaryKey(PkPrizeTakeLog record);
    //查询奖励记录表
    List<PrizeTakeLogVo> selectPkPrizeTakeLog(PrizeTakeLogDto prizeTakeLogDto);
    //根据记录id查询奖励详情
    PrizeTakeLogVo selectDetails(@Param("id") Integer id);
}