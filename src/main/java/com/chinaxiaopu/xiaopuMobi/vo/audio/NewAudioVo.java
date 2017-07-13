package com.chinaxiaopu.xiaopuMobi.vo.audio;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * 订阅频道中的最新的音频信息
 * Created by Wang on 2016/12/06.
 */
@Data
public class NewAudioVo {
    private Integer topicId;//音频Id
    private String content;//JSON串
    private Map<String,Object> contentMap;
//    private String posterImg;//音频图片
    private String slogan;//音频口号
    private Integer creatorId;//创建者Id
    private String creatorNickname;//创建者昵称
    private String creatorAvatar;//创建者头像

    public Map<String, Object> getContentMap() {
        if(!StringUtils.isEmpty(content)){
            contentMap = new Gson().fromJson(content,Map.class);
        }
        return contentMap;
    }

    public void setContentMap(Map<String, Object> contentMap) {
        this.contentMap = contentMap;
    }
}
