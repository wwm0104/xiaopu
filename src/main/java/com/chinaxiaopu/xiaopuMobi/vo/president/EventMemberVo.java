package com.chinaxiaopu.xiaopuMobi.vo.president;

import com.chinaxiaopu.xiaopuMobi.model.Event;
import com.chinaxiaopu.xiaopuMobi.model.EventMember;
import com.chinaxiaopu.xiaopuMobi.model.Group;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import lombok.Getter;
import lombok.Setter;
/**
 * Created by Wang on 2016/11/7.
 */
public class EventMemberVo extends EventMember{

    @Setter @Getter
    private Event event;
    @Setter @Getter
    private Group group;
    @Setter @Getter
    private UserInfo userInfo;
    @Setter @Getter
    private Integer isGroupMember;

}