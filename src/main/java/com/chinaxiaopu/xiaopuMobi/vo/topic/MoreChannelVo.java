package com.chinaxiaopu.xiaopuMobi.vo.topic;

import lombok.Data;

import java.util.List;

/**
 * 更多频道 vo
 * Created by 武宁 on 2016/12/1.
 */
@Data
public class MoreChannelVo {
    private Integer chanId;
    private String slogan;
    private String posterImg;
    private Integer sort;//越大越靠前
    private Integer more;
    private List<MoreChildChannelVo> childList;


}
