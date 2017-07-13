package com.chinaxiaopu.xiaopuMobi.vo;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.Map;

/**
 * Created by Ellie on 2016/12/6.
 */
@Data
public class UserFocusVo {

    private Integer topicId;//音频贴ID
    private String slogan;//音频贴名称
    private String content;//音频内容
    private String createTime;//创建时间


    private Integer userId; //关注主播的id
    private String avatarUrl;//主播头像
    private String nickName;//主播名
    private Integer userSex;//主播性别

    private Integer channelId;//频道id
    private String channelName;//频道名
    private String posterImg;//频道图片

    private Integer isFocus;//是否已经关注

    private Map<String, Object> contentMap;

    public Map<String, Object> getContentMap() {
        return contentMap;
    }

    public void setContentMap(String content) {
        Gson gson = new Gson();
        this.contentMap = gson.fromJson(content, Map.class);
    }

}
