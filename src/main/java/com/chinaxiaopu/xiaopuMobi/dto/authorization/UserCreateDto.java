package com.chinaxiaopu.xiaopuMobi.dto.authorization;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import lombok.Data;

/**
 * Created by Ellie on 2016/12/5.
 */
@Data
public class UserCreateDto extends ContextDto {
    private UserInfo userInfo;
    private String password;

}
