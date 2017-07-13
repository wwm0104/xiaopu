package com.chinaxiaopu.xiaopuMobi.quartz;

import com.chinaxiaopu.xiaopuMobi.service.topic.EventRankService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 达人榜
 * Created by Administrator on 2016/11/4.
 */
@Slf4j
public class UserDaRen implements Job{
    @Autowired
    private EventRankService eventRankService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("达人榜 ---开始!");
        try {
            eventRankService.userDaren();
            log.info("job scheduler = {}.", context.getScheduler().getSchedulerName());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        log.info("结束 ---job trigger = {}.", context.getTrigger());
    }
}
