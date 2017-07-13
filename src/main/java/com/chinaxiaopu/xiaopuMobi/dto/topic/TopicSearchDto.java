package com.chinaxiaopu.xiaopuMobi.dto.topic;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 根据图文标题搜索列表 新添加字段
 * Created by Administrator on 2016/11/2.
 */
@Data
public class TopicSearchDto extends ContextDto {
    private String slogan;//
    private Integer userId;
    private String timePoint;
}
