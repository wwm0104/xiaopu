package com.chinaxiaopu.xiaopuMobi.vo.topic;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Wang on 2016/11/22.
 */
@Data
public class TopicListVos {

    private List<TopicVo> list;
    private Date timePoint;

}