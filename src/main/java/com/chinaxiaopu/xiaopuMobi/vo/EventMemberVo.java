package com.chinaxiaopu.xiaopuMobi.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Wang on 2016/12/05.
 */

public class EventMemberVo {
    @Setter @Getter
    private Integer eventId;
    @Setter @Getter
    private String eventName;
    @Setter @Getter
    private Integer joinCnt;
    @Setter @Getter
    private Integer applyCnt;
}
