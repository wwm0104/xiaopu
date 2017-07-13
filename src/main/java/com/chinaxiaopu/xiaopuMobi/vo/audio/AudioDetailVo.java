package com.chinaxiaopu.xiaopuMobi.vo.audio;

import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by hao on 2016/12/6.
 */
public class AudioDetailVo extends Topic{
    private Integer topSort;
    private Integer recommentSort;
    private Map<String, Object> contentMap;
    private Map<String, Object> furtherMap;
    public Map<String, Object> getContentMap() {
        return contentMap;
    }
    public void setContentMap(String content) {
        Gson gson = new Gson();
        this.contentMap = gson.fromJson(content, Map.class);
    }

    public Map<String, Object> getFurtherMap() {
        return furtherMap;
    }
    public void setFurtherMap(String further) {
        Gson gson = new Gson();
        this.furtherMap = gson.fromJson(further, Map.class);
    }

    private String onlineTime;

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date createTime) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.onlineTime = sdf.format(createTime);
    }
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
