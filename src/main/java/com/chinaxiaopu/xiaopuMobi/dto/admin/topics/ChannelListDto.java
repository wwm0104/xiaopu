package com.chinaxiaopu.xiaopuMobi.dto.admin.topics;

import com.chinaxiaopu.xiaopuMobi.model.BaseEntity;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * Created by wuning on 2016/11/8.
 */
@Data
public class ChannelListDto extends BaseEntity{
    private Integer id;
    private String name;
    private Integer num;
    private String images;
    private String path;
    private Integer isOfficial;
    private Integer status;
    private String desc;
    private Integer sort;
    private Integer type;

}
