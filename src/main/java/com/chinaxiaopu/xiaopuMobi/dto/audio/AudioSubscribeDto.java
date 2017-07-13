package com.chinaxiaopu.xiaopuMobi.dto.audio;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import lombok.Data;

/**
 * 音频订阅DTO
 * Created by wuning on 2016/12/6.
 */
@Data
public class AudioSubscribeDto extends ContextDto{
    private Integer userId;
    private Integer channelId;
}
