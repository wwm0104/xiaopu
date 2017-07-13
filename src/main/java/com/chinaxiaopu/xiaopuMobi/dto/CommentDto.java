package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * Created by Administrator on 2016/11/2.
 */
public @Data
class CommentDto extends ContextDto {
    private Integer topicId;//话题id
    private String comment;//评论信息
}
