package com.chinaxiaopu.xiaopuMobi.dto;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import lombok.Data;

/**
 * Created by hao on 2016/10/28.
 */
public @Data class AdminListDto extends ContextDto {

    private String realName;  //姓名
    private String password; //密码

    private UserInfo userInfo;

}
