package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.BannerImg;
import com.chinaxiaopu.xiaopuMobi.model.BannerImgExample;
import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.topic.BannerVo;
import org.apache.ibatis.annotations.Param;

public interface BannerImgMapper {
    long countByExample(BannerImgExample example);

    int deleteByExample(BannerImgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BannerImg record);

    int insertSelective(BannerImg record);

    List<BannerImg> selectByExample(BannerImgExample example);

    BannerImg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BannerImg record, @Param("example") BannerImgExample example);

    int updateByExample(@Param("record") BannerImg record, @Param("example") BannerImgExample example);

    int updateByPrimaryKeySelective(BannerImg record);

    int updateByPrimaryKey(BannerImg record);

    List<BannerImg> selectBySort(@Param("bannerType") Integer bannerType); //前台查询Banner图

    List<BannerVo> selectBySort1(BannerImg bannerImg); //后台查询Banner列表
}