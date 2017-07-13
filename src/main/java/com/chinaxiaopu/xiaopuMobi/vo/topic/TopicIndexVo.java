package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.google.gson.Gson;
import lombok.Data;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/22.
 */
@Data
public class TopicIndexVo {
    private Integer topicId;
    private Integer type;
    private String slogan;
    private String content;
    private String expireTime;
    private Integer parentType;
    private String userName;
    private String prizeName;
    private Map<String,Object> contentMap;



    public  Map<String,Object> getContentMap() {
        return contentMap;
    }

    public  void setContentMap(String content) {
        Gson gson = new Gson();
        this.contentMap = gson.fromJson(content,Map.class);
    }

}
