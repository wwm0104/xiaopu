package com.chinaxiaopu.xiaopuMobi.vo.admin.quartz;

import lombok.Data;

/**
 * 姓名 ：武宁
 * 日期 ：2016/11/15
 * 备注 ： 定时器管理Vo
 */
@Data
public class QuartzVo {
    private  String triggerName;// 定时器trigger名称
    private  String jobName;//job名称
    private  String nextFireTime;//下次执行时间
    private  String prevFireTime;//
    private  Integer privrity;//优先级
    private  String triggerState;//执行状态 waiting 等待
    private  String triggerType;//执行类型 cron表达式
    private  String startTime;// 定时器创建时间
    private  String misfireInstr;//
    private  String cronExpression;//表达式
    private String groupName;
    private String triggerGroupName;
}
