package com.chinaxiaopu.xiaopuMobi.vo.eventLottery;

import lombok.Data;

/**
 * 活动抽奖 中奖信息 vo
 * Created by wuning on 2016/12/10.
 */
@Data
public class EventLotteryUserVo {
    private Integer row; //排序
    private Integer userId; //用户id
    private String userName; //用户名称
    private Integer phone;   //手机号
    private Integer userSex; //用户性别
    private String userImages; //用户头像
}
