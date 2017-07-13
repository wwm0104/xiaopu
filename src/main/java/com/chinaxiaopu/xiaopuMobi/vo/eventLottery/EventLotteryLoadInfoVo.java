package com.chinaxiaopu.xiaopuMobi.vo.eventLottery;

import com.chinaxiaopu.xiaopuMobi.vo.topic.UserPkVo;
import lombok.Data;

import java.util.List;

/**
 * 接口 ： 抽奖界面数据初始化 vo
 * Created by 武宁 on 2016/12/10.
 */
@Data
public class EventLotteryLoadInfoVo {
    private Integer round; //轮数
    private String eventName; //活动名称
    private String posterImg; //校普LOGO

    private List<EventLotteryUserVo> userList; //所有待抽奖的用户

    private List<EventLotteryUserVo> winnersList; //中奖人信息

}
