package com.chinaxiaopu.xiaopuMobi.vo.eventLottery;

import lombok.Data;

/**
 * Created by hao on 2016/12/10.
 */
@Data
public class LotteryListVo {
    private Integer eventId;
    private String eventName;
    Object[] roundList;
}
