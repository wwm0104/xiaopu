package com.chinaxiaopu.xiaopuMobi.vo.audio;

import lombok.Data;

/**
 * 接口 ： 音频频道列表
 * Created by wuning on 2016/12/5.
 */
@Data
public class AudioChannelListVo {
    private Integer channelId; //音频频道ID
    private String slogan; //音频频道名称
    private String posterImg;//音频频道图片
}
