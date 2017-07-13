package com.chinaxiaopu.xiaopuMobi.vo.topic;

import lombok.Data;

/**
 * 投票汇总
 * Created by Administrator on 2016/11/2.
 */
@Data
public class FindChannelVo{
    private String name;
    private Integer parentType;
    private Integer voteCnt;
    private String images;
    private String path;

}
