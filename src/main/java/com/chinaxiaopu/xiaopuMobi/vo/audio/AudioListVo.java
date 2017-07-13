package com.chinaxiaopu.xiaopuMobi.vo.audio;

import com.chinaxiaopu.xiaopuMobi.vo.ContextVo;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 频道中的音频列表
 * Created by Wang on 2016/12/06.
 */
@Data
public class AudioListVo extends ContextVo{
    private Date timePoint;//时间点，用来避免数据分页造成的数据重复出现
    private Integer audioCnt;//节目数量
    private List<AudioVo> list;
}
