package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/4.
 */
@Data
public class PKResultListVo {
    private Integer row;
    private Integer userId; //用户id
    private String userName;//用户昵称
    private String userImages;//用户图片
    private Integer voteCnt;//用户投票数
    private Integer isGet; //0 ：未获得奖励  1： 已获得奖励
    private String content;
    private Map<String,Object> contentMap;
    public  Map<String,Object> getContentMap() {
        return contentMap;
    }
    private String path;
    public  void setContentMap(String content) {
        Gson gson = new Gson();
        this.contentMap = gson.fromJson(content,Map.class);
    }

}
