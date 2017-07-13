package com.chinaxiaopu.xiaopuMobi.vo.partner;

import com.chinaxiaopu.xiaopuMobi.model.Partner;
import lombok.Data;

/**
 * Created by hao on 2016/11/4.
 */
@Data
public class GroupPartnerVo extends Partner {
    private Integer groupId;
    private String groupName;
}
