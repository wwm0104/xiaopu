package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * Created by Steven@chinaxiaopu.com on 10/5/16.
 */
@Data
public class GroupConfirmDto extends ContextDto {
    //审核人
    private Integer operator ;
    //社团编号
    private Integer groupId;
}
