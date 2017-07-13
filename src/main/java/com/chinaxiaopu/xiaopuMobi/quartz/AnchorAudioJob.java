package com.chinaxiaopu.xiaopuMobi.quartz;

import com.chinaxiaopu.xiaopuMobi.service.SchoolService;
import com.chinaxiaopu.xiaopuMobi.service.audio.AdminAudioService;
import com.chinaxiaopu.xiaopuMobi.service.topic.PkFinishPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ycy on 2016/12/6.
 */
@Slf4j
public class AnchorAudioJob implements Job{


        @Autowired
        AdminAudioService adminAudioService;

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            log.info("任务执行。。。"+context.getJobDetail().getJobDataMap().getInt("pkId"));
            //执行
            adminAudioService.updateIsDeleteById(context.getJobDetail().getJobDataMap().getInt("pkId"));

        }
    }


