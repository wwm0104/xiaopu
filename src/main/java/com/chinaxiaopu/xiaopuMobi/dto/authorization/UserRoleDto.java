package com.chinaxiaopu.xiaopuMobi.dto.authorization;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by Ellie on 2016/12/2.
 */
@Data
public class UserRoleDto {
    private List<Integer> roleIdList;
    private UserInfo userInfo;
}
