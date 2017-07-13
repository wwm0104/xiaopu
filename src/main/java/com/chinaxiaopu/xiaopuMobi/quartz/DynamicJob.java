package com.chinaxiaopu.xiaopuMobi.quartz;

import com.chinaxiaopu.xiaopuMobi.service.SchoolService;
import com.chinaxiaopu.xiaopuMobi.service.topic.PkFinishPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by liuwei
 * date: 2016/11/2
 */
@Slf4j
public class DynamicJob implements Job {

    @Autowired
    SchoolService schoolService;

    @Autowired
    PkFinishPrizeService pkFinishPrizeService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("任务执行。。。"+context.getJobDetail().getJobDataMap().getInt("pkId"));
        //执行
        pkFinishPrizeService.PkFinish(context.getJobDetail().getJobDataMap().getInt("pkId"));

    }
}
