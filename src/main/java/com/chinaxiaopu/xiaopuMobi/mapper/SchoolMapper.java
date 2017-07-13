package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.School;
import com.chinaxiaopu.xiaopuMobi.model.SchoolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SchoolMapper {
    long countByExample(SchoolExample example);

    int deleteByExample(SchoolExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(School record);

    int insertSelective(School record);

    List<School> selectByExample(SchoolExample example);

    School selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") School record, @Param("example") SchoolExample example);

    int updateByExample(@Param("record") School record, @Param("example") SchoolExample example);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);

    @Select("SELECT * FROM schools")
    List<School> selectAll();
    



}