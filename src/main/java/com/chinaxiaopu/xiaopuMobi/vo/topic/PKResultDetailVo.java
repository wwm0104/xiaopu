package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.google.gson.Gson;
import lombok.Data;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/3.
 */
@Data
public class PKResultDetailVo {
    private Integer id;
    private Integer userId;
    private String userName;
    private String userImages;
    private String slogan;
    private String content;
    private Integer favoriteCnt;
    private Integer likeCnt;
    private Integer voteCnt;
    private Integer commentCnt;
    private Integer isLike;//是否点赞 0 ： 未点赞
    private Integer isFav;//是否收藏 0 ： 未收藏
    private Integer isGet;
    private Integer type;
    private String path;
    private Integer row;
    private Integer pkId;
    private Integer isVotes;
    private Integer userSex;
    private String prizeName;
    private Integer prizeId;

    private Map<String, Object> contentMap;

    public Map<String, Object> getContentMap() {
        return contentMap;
    }

    public void setContentMap(String content) {
        Gson gson = new Gson();
        this.contentMap = gson.fromJson(content, Map.class);
    }


}
