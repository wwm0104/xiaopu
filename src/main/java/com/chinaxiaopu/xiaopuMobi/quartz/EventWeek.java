package com.chinaxiaopu.xiaopuMobi.quartz;

import com.chinaxiaopu.xiaopuMobi.service.topic.EventRankService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 活动排行榜周榜
 * Created by Administrator on 2016/11/3.
 */
@Slf4j
public class EventWeek implements Job {
    @Autowired
    private EventRankService eventRankService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("活动排行榜周榜 ---开始!");
        try {
            eventRankService.eventWeek();
            log.info("job scheduler = {}.", context.getScheduler().getSchedulerName());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        log.info("结束 ---job trigger = {}.", context.getTrigger());
    }
}
