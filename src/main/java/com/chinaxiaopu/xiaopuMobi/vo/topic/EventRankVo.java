package com.chinaxiaopu.xiaopuMobi.vo.topic;

import lombok.Data;

/**
 * 活动排行版
 * Created by Administrator on 2016/11/2.
 */
@Data
public class EventRankVo{
    private Integer eventId;//活动id
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String subject;//活动名称
    private Integer groupId;//社团id
    private String groupName;//社团名称
    private Integer row;//排序
    private Integer topicCnt;//活动下主题数
    private String posterImg;
    private String path;
}
