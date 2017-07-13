package com.chinaxiaopu.xiaopuMobi.vo.authorization;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.sun.tools.corba.se.idl.InterfaceGen;
import lombok.Data;


/**
 * Created by Ellie on 2016/12/12.
 */
@Data
public class UserRoleVo extends UserInfo{
    private Integer roleId;
    private String roleName;
    private String roleKey;
    private String roleKeys;
}
