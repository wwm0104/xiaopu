package com.chinaxiaopu.xiaopuMobi.vo.president;

import com.chinaxiaopu.xiaopuMobi.model.Group;
import com.chinaxiaopu.xiaopuMobi.model.GroupMember;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import lombok.Getter;
import lombok.Setter;
/**
 * Created by Wang on 2016/11/7.
 */
public class GroupMemberVo extends GroupMember{

    @Setter @Getter
    private Group group;
    @Setter @Getter
    private UserInfo userInfo;

}