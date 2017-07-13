package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.admin.quartz.QuartzDto;
import com.chinaxiaopu.xiaopuMobi.vo.admin.quartz.QuartzVo;

import java.util.List;

/**
 * 姓名 ：武宁
 * 日期 ：2016/11/15
 * 备注 ： 定时器管理Mapper
 */
public interface QuartzMapper {
    /**
     * 查询定时器列表
     *
     * @param quartzDto
     * @return
     */
    List<QuartzVo> findList(QuartzDto quartzDto);
}
