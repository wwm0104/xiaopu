package com.chinaxiaopu.xiaopuMobi.dto.admin.channel;

import com.chinaxiaopu.xiaopuMobi.model.BaseEntity;
import lombok.Data;

/**
 * 频道列表父级目录
 * Created by 武宁 on 2016/12/1.
 */
@Data
public class ChannelListDto extends BaseEntity {
    private String name;
    private Integer chnanelId;
}
