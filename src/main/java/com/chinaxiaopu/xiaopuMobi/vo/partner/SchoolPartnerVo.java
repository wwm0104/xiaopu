package com.chinaxiaopu.xiaopuMobi.vo.partner;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by hao on 2016/11/5.
 */
@Data
public class SchoolPartnerVo extends UserInfo {
    private String code; //邀请码
    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date joinTime; //加入时间
}
