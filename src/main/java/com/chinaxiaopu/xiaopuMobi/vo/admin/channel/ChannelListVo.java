package com.chinaxiaopu.xiaopuMobi.vo.admin.channel;

import lombok.Data;

/**
 * 频道父级列表查询目录
 * Created by 武宁 on 2016/12/1.
 */
@Data
public class ChannelListVo {
    private Integer id;
    private String name;
    private String posterImg;
    private String desc;
    private Integer sort;

}
