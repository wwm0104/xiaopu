package com.chinaxiaopu.xiaopuMobi.vo.audio;

import com.chinaxiaopu.xiaopuMobi.vo.ContextVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订阅列表
 * Created by Wang on 2016/12/06.
 */
@Data
public class SubscribeListVo extends ContextVo{
    private Date timePoint;
    private List<SubscribeVo> list;
}
