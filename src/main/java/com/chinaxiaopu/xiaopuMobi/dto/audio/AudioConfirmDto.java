package com.chinaxiaopu.xiaopuMobi.dto.audio;

import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicConfirmDto;
import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.google.gson.Gson;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hao on 2016/12/6.
 */
public class AudioConfirmDto extends Topic {
    private Integer topSort;
    private Integer recommentSort;

    public Integer getTopSort() {
        return topSort;
    }

    public void setTopSort(Integer topSort) {
        this.topSort = topSort;
    }

    public Integer getRecommentSort() {
        return recommentSort;
    }

    public void setRecommentSort(Integer recommentSort) {
        this.recommentSort = recommentSort;
    }
}
