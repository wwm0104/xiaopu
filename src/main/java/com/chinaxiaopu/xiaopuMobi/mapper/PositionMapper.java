package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.audio.PositionDto;
import com.chinaxiaopu.xiaopuMobi.model.Position;
import com.chinaxiaopu.xiaopuMobi.model.PositionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PositionMapper {
    long countByExample(PositionExample example);

    int deleteByExample(PositionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Position record);

    int insertSelective(Position record);

    List<Position> selectByExample(PositionExample example);

    Position selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Position record, @Param("example") PositionExample example);

    int updateByExample(@Param("record") Position record, @Param("example") PositionExample example);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    /**
     * 查询职位列表
     * @param positionDto
     * @return
     */
    List<Position> selectByPositionDto(PositionDto positionDto);

    @Select("select count(1) from positions where position_name=#{positionName}")
    int selectCntByName(@Param("positionName") String positionName);
}