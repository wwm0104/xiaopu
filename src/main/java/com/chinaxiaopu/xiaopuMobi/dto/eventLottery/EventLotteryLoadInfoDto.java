package com.chinaxiaopu.xiaopuMobi.dto.eventLottery;

import lombok.Data;

/**
 * 初始化加载数据
 * Created by wuning  on 2016/12/10.
 */
@Data
public class EventLotteryLoadInfoDto {
    private Integer round; //轮数    1开始
    private Integer eventId; //活动ID
}
