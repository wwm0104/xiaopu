package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

/**
 * Created by hao on 2016/10/25.
 */
@Data
public class AppVersionDto extends ContextDto {

    private String appId;//平台id

    private String appName;//平台名称

    private String description;//更新详情


}
