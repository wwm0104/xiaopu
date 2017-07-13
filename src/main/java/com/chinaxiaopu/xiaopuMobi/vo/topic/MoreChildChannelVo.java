package com.chinaxiaopu.xiaopuMobi.vo.topic;

import lombok.Data;

/**
 * 更多 频道列表  子数据
 * Created by 武宁 on 2016/12/1.
 */
@Data
public class MoreChildChannelVo {
    private  Integer PId;
    private Integer channelId;
    private String slogan;
    private String posterImg;
    private Integer sort;//越大越靠前
    private Integer type;
}
