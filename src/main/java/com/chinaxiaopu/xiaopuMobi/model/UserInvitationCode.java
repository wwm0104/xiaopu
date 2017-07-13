package com.chinaxiaopu.xiaopuMobi.model;

import lombok.Data;

/**
 * Created by Administrator on 2016/11/8.
 */
@Data
public class UserInvitationCode {
    private Integer userId;
    private String invitationCode;
    private String userCode;
}
