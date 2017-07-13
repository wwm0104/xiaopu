package com.chinaxiaopu.xiaopuMobi.config;

import com.chinaxiaopu.xiaopuMobi.quartz.*;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by liuwei
 * date: 2016/11/2
 * 该类主要用于集群版本
 */
@Slf4j
@Configuration
@AutoConfigureBefore({DatasourceConfig.class})
public class QuartzConfig {

    @Autowired
    private ResourceLoader resourceLoader;

    private final static String CRON_EXPRESSION = "0/40 * * * * ?";

    private final static  String CRON_HOUR="0 0 0/1 * * ? *";//日 排行榜 每隔一小时
    private final static String CRON_DAY="0 0 0 * * ? *";//每天运行一次
    private final static String CRON_WEEK = "0 0 0 ? ? 1 ";//周末 运行一次
    private final static String CRON_MONTH = "0 0 0 1 * ? *";//月 末

    /**
     * 活动 日 排行榜   每小时启动一次
     *
     * @return
     */
    @Bean(name = "EventDayJob")
    public JobDetailFactoryBean creatEventDay() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName("EventDayJobName");
        jobDetailFactoryBean.setGroup("EventDayJobGroup");
        jobDetailFactoryBean.setJobClass(EventDay.class);
        return jobDetailFactoryBean;
    }

    @Bean(name = "EventDayCron")
    protected CronTriggerFactoryBean createEventTriggerDay(@Qualifier("EventDayJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("EventDayTriggerName");
        cronTriggerFactoryBean.setGroup("EventDayTriggerGroup");
        cronTriggerFactoryBean.setCronExpression(CRON_HOUR);//CRON_EXPRESSION CRON_HOUR
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cronTriggerFactoryBean;
    }

    /**
     * 活动排行榜 周    每天启动一次
     *
     * @return
     */
    @Bean(name = "EventWeekJob")
    public JobDetailFactoryBean creatEventWeek() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName("EventWeekJobName");
        jobDetailFactoryBean.setGroup("EventWeekJobGroup");
        jobDetailFactoryBean.setJobClass(EventWeek.class);
        return jobDetailFactoryBean;
    }

    @Bean(name = "EventWeekCron")
    protected CronTriggerFactoryBean createEventTriggerWeek(@Qualifier("EventWeekJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("EventWeekTriggerName");
        cronTriggerFactoryBean.setGroup("EventWeekTriggerGroup");
        cronTriggerFactoryBean.setCronExpression(CRON_DAY);//CRON_EXPRESSION CRON_DAY
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cronTriggerFactoryBean;
    }

    /**
     * 活动排行榜 月   每天启动一次
     *
     * @return
     */
    @Bean(name = "EventMonthJob")
    public JobDetailFactoryBean creatEventMonth() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName("EventMonthJobName");
        jobDetailFactoryBean.setGroup("EventMonthJobGroup");
        jobDetailFactoryBean.setJobClass(EventMonth.class);
        return jobDetailFactoryBean;
    }

    @Bean(name = "EventMonthCron")
    protected CronTriggerFactoryBean createEventTriggerMonth(@Qualifier("EventMonthJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("EventMonthTriggerName");
        cronTriggerFactoryBean.setGroup("EventMonthTriggerGroup");
        cronTriggerFactoryBean.setCronExpression(CRON_DAY);//CRON_EXPRESSION CRON_DAY
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cronTriggerFactoryBean;
    }


    /**
     * 投票 日 排行榜    每隔一小时启动一次
     *
     * @return
     */
    @Bean(name = "VotesDayJob")
    public JobDetailFactoryBean creatVotesDay() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName("VotesDayJobName");
        jobDetailFactoryBean.setGroup("VotesDayJobGroup");
        jobDetailFactoryBean.setJobClass(VotesDay.class);
        return jobDetailFactoryBean;
    }

    @Bean(name = "VotesDayCron")
    protected CronTriggerFactoryBean createVotesTriggerDay(@Qualifier("VotesDayJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("VotesDayTriggerName");
        cronTriggerFactoryBean.setGroup("VotesDayTriggerGroup");
        cronTriggerFactoryBean.setCronExpression(CRON_HOUR);//CRON_EXPRESSION CRON_HOUR
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cronTriggerFactoryBean;
    }

    /**
     * 投票排行榜 周    每天启动一次
     *
     * @return
     */
    @Bean(name = "VotesWeekJob")
    public JobDetailFactoryBean creatVotesWeek() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName("VotesWeekJobName");
        jobDetailFactoryBean.setGroup("VotesWeekJobGroup");
        jobDetailFactoryBean.setJobClass(VotesWeek.class);
        return jobDetailFactoryBean;
    }

    @Bean(name = "VotesWeekCron")
    protected CronTriggerFactoryBean createVotesTriggerWeek(@Qualifier("VotesWeekJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("VotesWeekTriggerName");
        cronTriggerFactoryBean.setGroup("VotesWeekTriggerGroup");
        cronTriggerFactoryBean.setCronExpression(CRON_DAY);//CRON_EXPRESSION CRON_DAY
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cronTriggerFactoryBean;
    }

    /**
     * 投票排行榜 月    每天启动一次
     *
     * @return
     */
    @Bean(name = "VotesMonthJob")
    public JobDetailFactoryBean creatVotesMonth() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName("VotesMonthJobName");
        jobDetailFactoryBean.setGroup("VotesMonthJobGroup");
        jobDetailFactoryBean.setJobClass(VotesMonth.class);
        return jobDetailFactoryBean;
    }

    @Bean(name = "VotesMonthCron")
    protected CronTriggerFactoryBean createVotesTriggerMonth(@Qualifier("VotesMonthJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("VotesMonthTriggerName");
        cronTriggerFactoryBean.setGroup("VotesMonthTriggerGroup");
        cronTriggerFactoryBean.setCronExpression(CRON_DAY);//CRON_EXPRESSION CRON_DAY
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cronTriggerFactoryBean;
    }

    /**
     * 不服榜单   每天启动一次
     *
     * @return
     */
    @Bean(name = "UserBuFuJob")
    public JobDetailFactoryBean creatUserBuFu() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName("UserBuFuJobName");
        jobDetailFactoryBean.setGroup("UserBuFuJobGroup");
        jobDetailFactoryBean.setJobClass(UserBuFu.class);
        return jobDetailFactoryBean;
    }

    @Bean(name = "UserBuFuCron")
    protected CronTriggerFactoryBean createUserBuFuTrigger(@Qualifier("UserBuFuJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("UserBuFuTriggerName");
        cronTriggerFactoryBean.setGroup("UserBuFuTriggerGroup");
        cronTriggerFactoryBean.setCronExpression(CRON_HOUR);//CRON_EXPRESSION CRON_DAY
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cronTriggerFactoryBean;
    }

    /**
     * 达人榜   每天启动一次
     *
     * @return
     */
    @Bean(name = "UserDaRenJob")
    public JobDetailFactoryBean creatUserDaRen() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName("UserDaRenJobName");
        jobDetailFactoryBean.setGroup("UserDaRenJobGroup");
        jobDetailFactoryBean.setJobClass(UserDaRen.class);
        return jobDetailFactoryBean;
    }

    @Bean(name = "UserDaRenCron")
    protected CronTriggerFactoryBean createUserDaRenTrigger(@Qualifier("UserDaRenJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("UserDaRenTriggerName");
        cronTriggerFactoryBean.setGroup("UserDaRenTriggerGroup");
        cronTriggerFactoryBean.setCronExpression(CRON_HOUR);//CRON_EXPRESSION CRON_DAY
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cronTriggerFactoryBean;
    }

    /**
     * 投票汇总   每小时启动一次
     *
     * @return
     */
    @Bean(name = "VotesPoolJob")
    public JobDetailFactoryBean creatVotesPool() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName("VotesPoolJobName");
        jobDetailFactoryBean.setGroup("VotesPoolJobGroup");
        jobDetailFactoryBean.setJobClass(VotesPool.class);
        return jobDetailFactoryBean;
    }

    @Bean(name = "VotesPoolCron")
    protected CronTriggerFactoryBean createVotesPoolTrigger(@Qualifier("VotesPoolJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("VotesPoolTriggerName");
        cronTriggerFactoryBean.setGroup("VotesPoolTriggerGroup");
        cronTriggerFactoryBean.setCronExpression(CRON_HOUR);
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cronTriggerFactoryBean;
    }

    /**
     * 后台奖品 当超过有效期或数量为零，自动停止
     *
     * @return
     */
    @Bean(name = "PrizeJob")
    public JobDetailFactoryBean createPrizeDay() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName("PrizeJobName");
        jobDetailFactoryBean.setGroup("PrizeJobGroup");
        jobDetailFactoryBean.setJobClass(PrizeQuartz.class);
        return jobDetailFactoryBean;
    }

    @Bean(name = "PrizeCron")
    protected CronTriggerFactoryBean createPrizeTriggerDay(@Qualifier("PrizeJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("PrizeTriggerName");
        cronTriggerFactoryBean.setGroup("PrizeTriggerGroup");
        cronTriggerFactoryBean.setCronExpression(CRON_DAY);//CRON_EXPRESSION CRON_HOUR
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cronTriggerFactoryBean;
    }




    @Bean
    public AutoWiringSpringBeanJobFactory autoWiringSpringBeanJobFactory() {
        return new AutoWiringSpringBeanJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(List<Trigger> triggers, DataSource dataSource) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(triggers.toArray(new Trigger[0]));
        Resource resource = resourceLoader.getResource("classpath:quartz.properties");
        schedulerFactoryBean.setConfigLocation(resource);
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setJobFactory(autoWiringSpringBeanJobFactory());
        return schedulerFactoryBean;
    }

}
