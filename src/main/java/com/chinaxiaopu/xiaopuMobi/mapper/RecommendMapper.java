package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.Recommend;
import com.chinaxiaopu.xiaopuMobi.model.RecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecommendMapper {
    long countByExample(RecommendExample example);

    int deleteByExample(RecommendExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Recommend record);

    int insertSelective(Recommend record);

    List<Recommend> selectByExample(RecommendExample example);

    Recommend selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Recommend record, @Param("example") RecommendExample example);

    int updateByExample(@Param("record") Recommend record, @Param("example") RecommendExample example);

    int updateByPrimaryKeySelective(Recommend record);

    int updateByPrimaryKey(Recommend record);
}