package com.chinaxiaopu.xiaopuMobi.vo.audio;

import com.google.gson.Gson;
import lombok.Data;

import java.util.Map;

/**
 * 接口 ： 音频置顶VO
 * Created by wuning on 2016/12/6.
 */
@Data
public class TopListVo {
    private Integer topicId; //音频贴ID
    private String solgan; //音频贴名称
    private String content; //详情

    private Integer channelId; //频道ID
    private String channelName; //频道名称
    private String posterImg; //频道图片

    private Integer userId;
    private String nickName;
    private Integer userSex;
    private String avatarUrl;

    private Integer playCnt = 0; //播放次数
    private String updateTime; //更新时间


    private Map<String, Object> contentMap;

    public Map<String, Object> getContentMap() {
        return contentMap;
    }

    public void setContentMap(String content) {
        Gson gson = new Gson();
        this.contentMap = gson.fromJson(content, Map.class);
    }

}
