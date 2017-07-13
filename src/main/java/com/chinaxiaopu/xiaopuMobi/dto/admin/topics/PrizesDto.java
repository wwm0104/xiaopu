package com.chinaxiaopu.xiaopuMobi.dto.admin.topics;

import lombok.Data;

/**
 * 奖品推荐
 * Created by Administrator on 2016/11/29.
 */
@Data
public class PrizesDto {
    private Integer id;
    private Integer prizesId;
    private Integer sort;
    private Integer parentType;
    private String recommendTime;
}
