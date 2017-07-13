package com.chinaxiaopu.xiaopuMobi.dto.topic;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import lombok.Data;

/**
 * Created by Wang
 * date: 16/11/02
 */
public @Data class TopicIdDto extends ContextDto{
    private Integer topicId;
    private Integer type;
}
