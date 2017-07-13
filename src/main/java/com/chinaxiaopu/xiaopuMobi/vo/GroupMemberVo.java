package com.chinaxiaopu.xiaopuMobi.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Wang on 2016/12/05.
 */

public class GroupMemberVo {
    @Setter @Getter
    private Integer groupId;
    @Setter @Getter
    private String groupName;
    @Setter @Getter
    private Integer joinCnt;
    @Setter @Getter
    private Integer applyCnt;
}
