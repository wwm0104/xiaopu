package com.chinaxiaopu.xiaopuMobi.quartz;

import com.chinaxiaopu.xiaopuMobi.service.topic.PrizeService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 奖品根据失效时间和数量进行定时扫描
 */
@Slf4j
public class PrizeQuartz implements Job {
    @Autowired
    private PrizeService prizeService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("奖品失效 扫描 ---开始!");
        try {
            prizeService.prizeScan();
            log.info("job scheduler = {}.", context.getScheduler().getSchedulerName());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        log.info("奖品失效 扫描 --- 结束 job trigger = {}.", context.getTrigger());
    }
}
