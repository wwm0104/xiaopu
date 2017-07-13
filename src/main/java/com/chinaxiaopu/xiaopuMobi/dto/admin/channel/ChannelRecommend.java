package com.chinaxiaopu.xiaopuMobi.dto.admin.channel;

import lombok.Data;

/**
 * Created by WUNING on 2016/12/2.
 */
@Data
public class ChannelRecommend {
    private Integer id;
    private Integer pid;
    private Integer sort;
    private Integer parentType;
    private String recommendTime;

}
