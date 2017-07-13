package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.AnchorPosition;
import com.chinaxiaopu.xiaopuMobi.model.AnchorPositionExample;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface AnchorPositionMapper {
    long countByExample(AnchorPositionExample example);

    int deleteByExample(AnchorPositionExample example);

    int insert(AnchorPosition record);

    int insertSelective(AnchorPosition record);

    List<AnchorPosition> selectByExample(AnchorPositionExample example);

    int updateByExampleSelective(@Param("record") AnchorPosition record, @Param("example") AnchorPositionExample example);

    int updateByExample(@Param("record") AnchorPosition record, @Param("example") AnchorPositionExample example);

    @Delete("DELETE from anchor_position where anchor_id=#{userId}")
    int deleteById(@Param("userId") Integer userId);  //删除权限
}