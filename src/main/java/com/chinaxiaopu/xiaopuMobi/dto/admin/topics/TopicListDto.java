package com.chinaxiaopu.xiaopuMobi.dto.admin.topics;

import com.chinaxiaopu.xiaopuMobi.model.BaseEntity;
import lombok.Data;

/**
 * Created by Wang on 2016/11/09.
 */
@Data
public class TopicListDto extends BaseEntity{
    private Integer type;//类型
    private Integer status;//状态
    private String keyword;//关键字
//    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private String startTime;//开始时间
//    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private String endTime;//结束时间

}
