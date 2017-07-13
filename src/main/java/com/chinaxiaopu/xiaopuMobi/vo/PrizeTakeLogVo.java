package com.chinaxiaopu.xiaopuMobi.vo;

import com.chinaxiaopu.xiaopuMobi.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import lombok.Data;
import org.apache.commons.collections.map.HashedMap;

import java.util.Date;
import java.util.Map;

/**
 * Created by hao on 2016/11/8.
 */
@Data
public class PrizeTakeLogVo extends BaseEntity {
    private String awardName; //发奖人
    private Integer id; //记录id
    private Integer pkId; //Pkid
    private Integer hasTake;  //是否领取
    private Integer userId; //领取人id
    private String rewardRealName; //领取人
    private String rewardNickName;  //领取人昵称
    private String mobile;
    private String studentNo;
    private String qq;
    private String groups; //所属列表
    private Integer topicId; //帖子id
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date takeTime; //领取时间
    private String prizeName; //物资名称
    private String slogan; //帖子标题
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;  //结束时间
    private String schoolName; //学校名称
    private String content; //帖子内容
    private Integer topicType; //帖子类型
    private Integer isChallenger; //1发起者 2挑战者
    private String rule;  //PK规则
    private Integer isFinish; //0没完成 1完成
    private int challengeCnt; //参加人数要求
    private int voteCnt; //投票人数要求
    private Integer takeVoteCnt; //总投票数
    private Integer takeTopicCnt; //参与PK人数
    private Map<String,Object> contentMap;  //图文内容map集合
    private String imgUrl; //服务器图片地址
    private Integer recommend; //推荐位
    private String tags;
    public void setRule(String rule){
        Gson gson=new Gson();
        Map<String,Object> map=new HashedMap();
        map=gson.fromJson(rule,Map.class);
        int challengeCnt=(int)((double)map.get("challengeCnt"));
        int voteCnt=(int)((double)map.get("voteCnt"));
        this.challengeCnt=challengeCnt;
        this.voteCnt=voteCnt;
    }
    public Map<String, Object> getContentMap() {
        return contentMap;
    }
    public void setContentMap(String content) {
        Gson gson = new Gson();
        this.contentMap = gson.fromJson(content, Map.class);
    }
}
