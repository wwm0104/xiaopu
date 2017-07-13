package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * Created by Steven@chinaxiaopu.com on 10/5/16.
 */
@Data
public class GroupUIDto extends ContextDto {
    //申请人
    private Integer userId;
    //社团编号
    private Integer groupId;
}
