package com.chinaxiaopu.xiaopuMobi.vo.topic;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * Created by Wang on 2016/11/7.
 */
public class PrizeListVo {
    @Setter @Getter
    private List<PrizeViewVo> cashList;
    @Setter @Getter
    private List<PrizeViewVo> physicalList;
    @Setter @Getter
    private List<PrizeViewVo> virtualList;
    @Setter @Getter
    private List<PrizeViewVo> redPacketList;
    @Setter @Getter
    private Integer cashNum;
}