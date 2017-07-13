package com.chinaxiaopu.xiaopuMobi.dto.authorization;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import lombok.Data;

import java.util.Date;

/**
 * Created by Ellie on 2016/11/16.
 */
public @Data class RolesDto extends ContextDto {

    private Integer roleId;
    private Integer userId;
    private String roleKey;
    private String roleName;
    private Date createTime;
    private String keyWord;

}
