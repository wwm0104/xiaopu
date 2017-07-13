package com.chinaxiaopu.xiaopuMobi.service.admin.quartz;

import com.chinaxiaopu.xiaopuMobi.dto.admin.quartz.QuartzDto;
import com.chinaxiaopu.xiaopuMobi.mapper.QuartzMapper;
import com.chinaxiaopu.xiaopuMobi.vo.admin.quartz.QuartzVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 姓名 ：武宁
 * 日期 ：2016/11/15
 * 备注 ： 定时器管理Service
 */
@Slf4j
@Service
public class QuartzService {
    @Autowired
    private QuartzMapper quartzMapper;

    /**
     * 武宁
     * 查询定时器列表
     *
     * @param quartzDto
     * @return
     */
    public PageInfo<QuartzVo> list(QuartzDto quartzDto) {
        if (quartzDto.getPage() != null && quartzDto.getRows() != null) {
            PageHelper.startPage(quartzDto.getPage(), quartzDto.getRows());
        }
        List<QuartzVo> list = quartzMapper.findList(quartzDto);
        for(QuartzVo vo : list){
            if("EventDayTriggerName".equals(vo.getTriggerName())){
             vo.setTriggerName("活动日排行");
            }
            if("EventWeekTriggerName".equals(vo.getTriggerName())){
                vo.setTriggerName("活动周排行");
            }
            if("EventMonthTriggerName".equals(vo.getTriggerName())){
                vo.setTriggerName("活动月排行");
            }
            if("VotesDayTriggerName".equals(vo.getTriggerName())){
                vo.setTriggerName("投票日排行");
            }
            if("VotesWeekTriggerName".equals(vo.getTriggerName())){
                vo.setTriggerName("投票周排行");
            }
            if("VotesMonthTriggerName".equals(vo.getTriggerName())){
                vo.setTriggerName("活动月排行");
            }
            if("UserBuFuTriggerName".equals(vo.getTriggerName())){
                vo.setTriggerName("不服榜");
            }
            if("UserDaRenTriggerName".equals(vo.getTriggerName())){
                vo.setTriggerName("达人榜");
            }
            if("VotesPoolTriggerName".equals(vo.getTriggerName())){
                vo.setTriggerName("投票总汇");
            }
            if("PrizeTriggerName".equals(vo.getTriggerName())){
                vo.setTriggerName("奖品库存");
            }

        }
        PageInfo<QuartzVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
