package com.chinaxiaopu.xiaopuMobi.quartz;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by liuwei
 * date: 2016/11/3
 */
@Slf4j
@Component
public class QuartzUtils {

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

	private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";

    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";  
     
    /**
     * 创建任务
     * @param name
     * @param clazz
     * @param cronExpression
     */
    public void addJob(String name,Class<? extends Job> clazz,String cronExpression,Integer pkId) {
        try {      
        	Scheduler scheduler=schedulerFactoryBean.getScheduler();
            //构造任务
            JobDetail job = JobBuilder.newJob(clazz)
                    .withIdentity(name, JOB_GROUP_NAME)                  
                    .build();
            job.getJobDataMap().put("pkId", pkId+"");
            //构造任务触发器
            Trigger trg = TriggerBuilder.newTrigger()
                    .withIdentity(name, TRIGGER_GROUP_NAME)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();       
             
            //将任务添加到调度器
            scheduler.scheduleJob(job, trg);
            log.info("创建任务=> [任务名称：" + name + " 任务组：" + JOB_GROUP_NAME + "] ");
         // 启动  
            if (!scheduler.isShutdown()){  
            	scheduler.start();  
            }  
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("创建任务=> [任务名称：" + name + " 任务组：" + JOB_GROUP_NAME + "]=> [失败]");
        }
    }
    /**
     * 删除任务
     * @param name
     */
    public void removeJob(String name){
        try {
        	Scheduler scheduler=schedulerFactoryBean.getScheduler();
            TriggerKey tk = TriggerKey.triggerKey(name, TRIGGER_GROUP_NAME);
            scheduler.pauseTrigger(tk);//停止触发器  
            scheduler.unscheduleJob(tk);//移除触发器
            JobKey jobKey = JobKey.jobKey(name, JOB_GROUP_NAME);
            scheduler.deleteJob(jobKey);//删除任务
            log.info("删除任务=> [任务名称：" + name + " 任务组：" + TRIGGER_GROUP_NAME + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("删除任务=> [任务名称：" + name + " 任务组：" + TRIGGER_GROUP_NAME + "]=> [失败]");
        }
    }
    /**
     *  暂停任务
     * @param name
     */
    public void pauseJob(String name){
        try {
        	Scheduler scheduler=schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(name, JOB_GROUP_NAME);
            scheduler.pauseJob(jobKey);
            log.info("暂停任务=> [任务名称：" + name + " 任务组：" + JOB_GROUP_NAME + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("暂停任务=> [任务名称：" + name + " 任务组：" + JOB_GROUP_NAME + "]=> [失败]");
        }
    }
     /**
      * 恢复任务
      * @param name
      */
    public void resumeJob(String name){
        try {
        	Scheduler scheduler=schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(name, JOB_GROUP_NAME);         
            scheduler.resumeJob(jobKey);
            log.info("恢复任务=> [任务名称：" + name + " 任务组：" + JOB_GROUP_NAME + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("恢复任务=> [任务名称：" + name + " 任务组：" + JOB_GROUP_NAME + "]=> [失败]");
        }       
    }
    /**
     * 修改任务触发时间
     * @param name
     * @param cronExpression
     */
    public void modifyTime(String name,String cronExpression){
        try {
        	Scheduler scheduler=schedulerFactoryBean.getScheduler();
            TriggerKey tk = TriggerKey.triggerKey(name, TRIGGER_GROUP_NAME);
            //构造任务触发器
            Trigger trg = TriggerBuilder.newTrigger()
                    .withIdentity(name, TRIGGER_GROUP_NAME)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();       
            scheduler.rescheduleJob(tk, trg);
            log.info("修改任务触发时间 => [任务名称：" + name + " 任务组：" + TRIGGER_GROUP_NAME + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("修改任务触发时间=> [任务名称：" + name + " 任务组：" + TRIGGER_GROUP_NAME + "]=> [失败]");
        }
    }
 
    public void start() {
        try {
        	Scheduler scheduler=schedulerFactoryBean.getScheduler();
            /*scheduler.start();*/
            scheduler.resumeAll();
            log.info("启动调度器 ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("启动调度器=> [失败]");
        }
    }
 
    public void shutdown() {
        try {
        	Scheduler scheduler=schedulerFactoryBean.getScheduler();
            /*scheduler.shutdown();*/
            scheduler.pauseAll();
            log.info("停止调度器 ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("停止调度器=> [失败]");
        }
    }
    /**
     * 获取当前所有Job和Trigger
     * @return
     */
	public Map<String, Object> getAllJobTrigger(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Scheduler sched = schedulerFactoryBean.getScheduler();
			Set<JobKey> setjob=sched.getJobKeys(GroupMatcher.jobGroupEquals(JOB_GROUP_NAME));
			Set<TriggerKey> settrig=sched.getTriggerKeys(GroupMatcher.triggerGroupEquals(TRIGGER_GROUP_NAME));
			map.put("jobs", setjob);
			map.put("jobs_count", setjob == null ? 0 : setjob.size());
			map.put("triggers", settrig);
			map.put("triggers_count", settrig == null ? 0 : settrig.size());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return map;
	}
	
    /**
     * 时间转换
     *
     * */
	public String toimestampToCron(Timestamp times){
        int sec = times.getSeconds();
		int min = times.getMinutes();
		int hou = times.getHours();
		int day = times.getDate();
		int mon = times.getMonth() + 1;
		int yea = times.getYear() + 1900;
		return sec + " " + min + " " + hou + " " + day + " " + mon + " ? " + yea;
    }

    /**
     * 判断调度是否启动
     * @return
     */
    public boolean isStart(){
        boolean flag=false;
        try {
            Scheduler scheduler=schedulerFactoryBean.getScheduler();
            flag  = scheduler.isStarted();
            log.info("调度  是否启动 ");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 判断调度是否停止
     * @return
     */
    public boolean isShutDown(){
        boolean flag=false;
        try {
            Scheduler scheduler=schedulerFactoryBean.getScheduler();
            flag  = scheduler.isShutdown();
            log.info("判断调度是否停止 ");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 暂停定时器 new 武宁
     * @param name
     */
    public void new_pauseJob(String name,String group) {
        try {
            Scheduler scheduler=schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(name, group);
            scheduler.pauseJob(jobKey);
            log.info("暂停任务 => [任务名称：" + name + " 任务组：" + group + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("暂停任务 => [任务名称：" + name + " 任务组：" + group + "]=> [失败]");
        }
    }

    /**
     * 恢复定时器 new 武宁
     * @param name
     * @param group
     */
    public void new_resumeJob(String name, String group) {
        try {
            Scheduler scheduler=schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(name, group);
            scheduler.resumeJob(jobKey);
            log.info("恢复任务 => [任务名称：" + name + " 任务组：" + group + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("恢复任务 => [任务名称：" + name + " 任务组：" + group + "]=> [失败]");
        }
    }

    /**
     * 删除定时器 new 武宁
     * @param name
     * @param triggerGroupName
     */
    public void new_removeJob(String name, String triggerGroupName,String group) {
        try {
            Scheduler scheduler=schedulerFactoryBean.getScheduler();
            TriggerKey tk = TriggerKey.triggerKey(name, triggerGroupName);
            scheduler.pauseTrigger(tk);//停止触发器
            scheduler.unscheduleJob(tk);//移除触发器
            JobKey jobKey = JobKey.jobKey(name, group);
            scheduler.deleteJob(jobKey);//删除任务
            log.info("删除任务 => [任务名称：" + name + " 任务组：" + triggerGroupName + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("删除任务 => [任务名称：" + name + " 任务组：" + triggerGroupName + "]=> [失败]");
        }
    }

    /**
     * 修改 定时器时间
      * @param name
     * @param cronExpression
     * @param triggerGroupName
     */
    public void new_modifyTime(String name, String cronExpression, String triggerGroupName) {
        try {
            Scheduler scheduler=schedulerFactoryBean.getScheduler();
            TriggerKey tk = TriggerKey.triggerKey(name, triggerGroupName);
            //构造任务触发器
            Trigger trg = TriggerBuilder.newTrigger()
                    .withIdentity(name,triggerGroupName)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
            scheduler.rescheduleJob(tk, trg);
            log.info("修改任务触发时间    => [任务名称：" + name + " 任务组：" + triggerGroupName + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("修改任务触发时间    => [任务名称：" + name + " 任务组：" + triggerGroupName + "]=> [失败]");
        }
    }
}
