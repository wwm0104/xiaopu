package com.chinaxiaopu.xiaopuMobi.dto.admin.topics;

import lombok.Data;

/**
 * Created by Administrator on 2016/11/22.
 */
@Data
public class TopicIndexDto {
    private Integer id;
    private Integer topicId;
    private Integer sort;
    private Integer parentType;
    private String recommendTime;
}

