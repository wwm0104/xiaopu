package com.chinaxiaopu.xiaopuMobi.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Wang on 2016/12/02.
 */

public class UserInfoListVo {
    @Setter @Getter
    private List<UserInfoVo> list;
    @Setter @Getter
    private Integer isTicket = 0;
    @Setter @Getter
    private Integer isPresident = 0;
    @Setter @Getter
    private Integer signCnt;
}
