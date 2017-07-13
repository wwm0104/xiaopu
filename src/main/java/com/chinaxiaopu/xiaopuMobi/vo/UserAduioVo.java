package com.chinaxiaopu.xiaopuMobi.vo;

import lombok.Data;

/**
 * Created by Ellie on 2016/12/12.
 */
@Data
public class UserAduioVo {
    private Integer userId;
    private Integer topicId;//音频贴ID
    private String slogan;//音频贴名称
    private String content;//音频内容
    private String createTime;//创建时间

    private Integer channelId;//频道id
    private String channelName;//频道名
    private String posterImg;//频道图片
}
