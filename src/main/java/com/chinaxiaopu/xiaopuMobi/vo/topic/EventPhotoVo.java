package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.vo.ContextVo;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 活动相关主题的图片
 * Created by Wang on 2016/11/11.
 */

public class EventPhotoVo extends ContextVo{
    @Setter @Getter
    private Integer topicId;//活动相关主题id
    @Setter @Getter
    private String content;
    private Map<String,Object> contentMap;

    public Map<String, Object> getContentMap() {
        contentMap = new Gson().fromJson(content,Map.class);
        return contentMap;
    }
}
