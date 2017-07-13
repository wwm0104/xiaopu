package com.chinaxiaopu.xiaopuMobi.dto.partner;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import lombok.Data;


/**
 * Created by hao on 2016/11/5.
 */
@Data
public class PartnerDetailsDto extends ContextDto {
    private Integer partnerid;  //合伙人id
    private String code;  //邀请码
    private String realName; //用户姓名
    private Integer schoolId; //学校id
    private String  joinTime;  //加入时间
    private String startTime;
    private String endTime;
}
