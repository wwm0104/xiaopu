package com.chinaxiaopu.xiaopuMobi.dto.admin.topics;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wang on 2016/11/09.
 */

public class TopicConfirmDto{
    @Setter @Getter
    private Integer topicId;
    @Setter @Getter
    private Integer status;//主题状态
    @Setter @Getter
    private String reason;//驳回原因
    private String further;//json串，存储驳回原因
    @Setter @Getter
    private Integer userId;

    public String getFurther() {
        Map<String,String> map = new HashMap<>();
        map.put("reason",reason);
        further = new Gson().toJson(map);
        return further;
    }
}
