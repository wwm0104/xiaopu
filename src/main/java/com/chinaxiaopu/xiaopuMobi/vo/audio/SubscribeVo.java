package com.chinaxiaopu.xiaopuMobi.vo.audio;

import com.chinaxiaopu.xiaopuMobi.vo.ContextVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * 订阅列表
 * Created by Wang on 2016/12/06.
 */
@Data
public class SubscribeVo extends ContextVo{
    private Integer channelId; //频道ID
    private String channelPosterImg; //频道LOGO
    private String channelName; //频道名称
    private String channelDesc = "分享: 我的频道发布了新内容，快来抢先听！"; //频道内容

    private Integer topicId;//音频Id
    private String content;//JSON串
    private Map<String,Object> contentMap;
    private String slogan;//音频口号
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;//发布时间
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
