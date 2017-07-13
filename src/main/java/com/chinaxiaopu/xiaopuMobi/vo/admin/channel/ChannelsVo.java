package com.chinaxiaopu.xiaopuMobi.vo.admin.channel;

import com.chinaxiaopu.xiaopuMobi.model.PkChannel;
import lombok.Data;

/**
 * Created by wuning on 2016/12/14.
 */
@Data
public class ChannelsVo extends PkChannel{
    private Integer channelId;
    private String channelName;
    private Integer sort;

    private String channId;
}
