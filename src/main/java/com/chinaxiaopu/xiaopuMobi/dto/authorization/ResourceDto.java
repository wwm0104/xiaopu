package com.chinaxiaopu.xiaopuMobi.dto.authorization;

import lombok.Data;

/**
 * Created by Ellie on 2016/11/28.
 */
@Data
public class ResourceDto {
    private Integer id;
    private Integer parentId;
    private String icon;
    private Integer type;
    private String url;
    private String name;
    private String permission;
    private String sort;
    private String available;

}
