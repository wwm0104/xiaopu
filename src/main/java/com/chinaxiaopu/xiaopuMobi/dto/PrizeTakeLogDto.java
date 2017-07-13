package com.chinaxiaopu.xiaopuMobi.dto;

import com.chinaxiaopu.xiaopuMobi.model.BaseEntity;
import lombok.Data;

/**
 * Created by hao on 2016/11/8.
 */
@Data
public class PrizeTakeLogDto extends BaseEntity {
    private Integer topicType; //图文类型
    private String endTime; //结束时间
    private Integer hasTake; //领取状态
    private String takeTime; //领取时间
    private String startTime;
    private String time;
    private String takeStartTime;
    private String takeEndTime;
    private Integer userId;  //发奖人id
    private String realName;
}
