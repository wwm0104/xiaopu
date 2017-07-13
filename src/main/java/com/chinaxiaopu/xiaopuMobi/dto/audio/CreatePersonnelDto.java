package com.chinaxiaopu.xiaopuMobi.dto.audio;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import lombok.Data;

/**
 * Created by hao on 2016/12/7.
 */
@Data
public class CreatePersonnelDto extends UserInfo {
    private Integer positionId;
}
