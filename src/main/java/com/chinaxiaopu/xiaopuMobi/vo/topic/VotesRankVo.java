package com.chinaxiaopu.xiaopuMobi.vo.topic;


import com.google.gson.Gson;
import lombok.Data;

import java.util.Map;

/**
 * 投票排行版vo
 * Created by Administrator on 2016/11/2.
 */
@Data
public class VotesRankVo {
    private Integer row;//排序
    private Integer topicId;//投票主题id
    private Integer voteCnt;//投票参与人数
    private String slogan;//标题
    private String expireTime;//截止时间
    private String creatorNickname;//发起者
    private Integer userId;
    private String path;
    private Integer type;
    private Integer isOver;
    private Integer userSex;
    private String prizeName;
    private Integer prizeId;

    private Integer parentType;

    private String content;
    private Map<String,Object> contentMap;
    public  Map<String,Object> getContentMap() {
        return contentMap;
    }

    public  void setContentMap(String content) {
        Gson gson = new Gson();
        this.contentMap = gson.fromJson(content,Map.class);
    }


}
