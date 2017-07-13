package com.chinaxiaopu.xiaopuMobi.vo.audio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * 频道中的音频列表
 * Created by Wang on 2016/12/06.
 */
@Data
public class AudioVo {
    private Integer topicId;//音频Id
    private String content;//JSON串，获取topicLOGO
    private Map<String,Object> contentMap;
//    private String posterImg;//音频图片
    private String slogan;//音频口号
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;//创建时间
    private Integer playCnt;//播放次数
    private Integer commentCnt;//评论数
    private Integer likeCnt;//点赞数
    private Integer isLike;//是否点赞

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
