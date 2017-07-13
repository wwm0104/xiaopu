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
 * 频道详情
 * Created by Wang on 2016/12/06.
 */
@Data
public class AudioInfoVo extends ContextVo{
    private Integer topicId;//音频Id
    private String slogan;//音频标题
    private String content;//混合数据
    private Map<String,Object> contentMap;
    private Integer likeCnt;//点赞数
    private Integer commentCnt;//评论数
    private Integer playCnt = 0;//播放次数
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;//创建时间
    private Integer isLike = 0;//是否点赞

    private Integer channelId; //音频频道ID
    private String channelName; //音频频道名称
    private String posterImg;//音频频道LOGO
    private Integer subscribeCnt;//订阅数
    private Integer isSubscribe = 0;//是否订阅

    private Integer userId;//主播Id
    private String nickName;//主播昵称
    private String avatar;//主播头像
    private Integer isFocus = 0;//是否关注

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
