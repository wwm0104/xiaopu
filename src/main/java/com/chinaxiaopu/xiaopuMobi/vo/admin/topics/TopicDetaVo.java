package com.chinaxiaopu.xiaopuMobi.vo.admin.topics;

import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.google.gson.Gson;
import lombok.Data;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/10.
 */
@Data
public class TopicDetaVo extends Topic{
    private String mobile;
    private String qq;
    private String groupName;
    private String tagName;
    private String studentNo;
    private Integer topicId;
    private String realName;
    private Integer tipOffCount;
    private Integer challengeNum = 0;
    private Integer rewardNum;
    private Map<String, Object> contentMap;
    private Map<String,Object>  rewardMap;
    private String path;
    private Integer isOver;
    private String prizeName;
    private Integer prizeType;
    private String prize;
    private Integer challenteCount;
    private Integer isFinish;
    private Integer voteCnt=0;
    private String slogan;

    private int challengeCnt;
    private int vCote;
    private String desc;
    private Integer isSuccess;




    public Map<String, Object> getRewardMap() {
        return rewardMap;
    }

    public void setRewardMap(String content) {
        Gson gson = new Gson();
        this.rewardMap = gson.fromJson(content, Map.class);
    }

    public Map<String, Object> getContentMap() {
        return contentMap;
    }

    public void setContentMap(String content) {
        Gson gson = new Gson();
        this.contentMap = gson.fromJson(content, Map.class);
    }
}
