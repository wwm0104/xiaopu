package com.chinaxiaopu.xiaopuMobi.vo.topic;

import lombok.Data;

/**
 * Created by hao on 2016/12/1.
 */
@Data
public class UserFanCountsVo {
    private Integer myTopicCnt;  //我的图文总数
    private Integer myFavCnt;    //我的收藏总数
    private Integer myFanCnt;    //我的粉丝总数
    private Integer myFollowCnt; //我的关注总数
}
