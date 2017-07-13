package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.Tipoff;
import com.chinaxiaopu.xiaopuMobi.model.TipoffExample;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.TipoffVo;
import org.apache.ibatis.annotations.Param;

public interface TipoffMapper {
    long countByExample(TipoffExample example);

    int deleteByExample(TipoffExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Tipoff record);

    int insertSelective(Tipoff record);

    List<Tipoff> selectByExample(TipoffExample example);

    Tipoff selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Tipoff record, @Param("example") TipoffExample example);

    int updateByExample(@Param("record") Tipoff record, @Param("example") TipoffExample example);

    int updateByPrimaryKeySelective(Tipoff record);

    int updateByPrimaryKey(Tipoff record);

    int selectCountByTopicIdAndUserId(@Param("tipoffId") Integer tipoffId, @Param("userId") Integer userId);

    int selectCountByTipoffIdAndUserIdAndTipoffType(@Param("tipoffId") Integer tipoffId, @Param("userId") Integer userId, @Param("tipoffType") Integer tipoffType);

    /**
     * 投诉列表
     *
     * @param tipoffId
     * @return
     */
    List<TipoffVo> selectTipOffList(Integer tipoffId);
}