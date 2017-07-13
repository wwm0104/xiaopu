package com.chinaxiaopu.xiaopuMobi.vo.eventLottery;

import com.chinaxiaopu.xiaopuMobi.model.EventLottery;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 活动抽奖列表
 * Created by Wang on 2016/12/12.
 */
@Data
public class EventLotteryVo extends EventLottery{
    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date updateTime;
}
