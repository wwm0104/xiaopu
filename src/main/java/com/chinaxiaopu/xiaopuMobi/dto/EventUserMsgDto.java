package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * Created by liuwei
 * date: 2016/12/10
 */
@Data
public class EventUserMsgDto extends ContextDto {

    private Integer userId;
    private String userName;
    private String avatarUrl;
    private Integer eventId;
    private String eventName;
    private String content;
    private Integer type;

}
