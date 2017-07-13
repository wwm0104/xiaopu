package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.VrActivity;
import com.chinaxiaopu.xiaopuMobi.model.VrActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VrActivityMapper {
    long countByExample(VrActivityExample example);

    int deleteByExample(VrActivityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VrActivity record);

    int insertSelective(VrActivity record);

    List<VrActivity> selectByExample(VrActivityExample example);

    VrActivity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VrActivity record, @Param("example") VrActivityExample example);

    int updateByExample(@Param("record") VrActivity record, @Param("example") VrActivityExample example);

    int updateByPrimaryKeySelective(VrActivity record);

    int updateByPrimaryKey(VrActivity record);

    List<VrActivity> selectByDate(@Param("appointmentDate")String appointmentDate);

    int updateCntById(@Param("vrActivity")VrActivity vrActivity);
}