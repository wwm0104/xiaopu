package com.chinaxiaopu.xiaopuMobi.dto;

import com.chinaxiaopu.xiaopuMobi.model.Partner;
import lombok.Data;

/**
 * Created by liuwei
 * date: 2016/10/21
 */
public @Data class PartnerDto extends Partner {

    private String smsCaptcha;
}
