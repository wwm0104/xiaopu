package com.chinaxiaopu.xiaopuMobi.vo;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Wang on 2016/11/12.
 */

public class UserInfoVo extends UserInfo{
    @Setter @Getter
    private Integer isGroupMember;
    @Setter @Getter @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date joinTime;
    @Setter @Getter
    private Integer isSignIn = 0;
}
