package com.chinaxiaopu.xiaopuMobi.vo.audio;

import com.chinaxiaopu.xiaopuMobi.vo.ContextVo;
import lombok.Data;

import java.util.List;

/**
 * 频道详情
 * Created by Wang on 2016/12/06.
 */
@Data
public class ChannelVo extends ContextVo{
    private Integer channelId; //音频频道ID
    private String channelName; //音频频道名称
    private String desc;//频道简介
    private String posterImg;//音频频道LOGO
    private Integer isSubscribe = 0;//是否订阅
    private List<AnchorVo> anchorList;//主播列表

}
