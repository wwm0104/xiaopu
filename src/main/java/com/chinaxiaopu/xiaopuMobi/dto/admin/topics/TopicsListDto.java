package com.chinaxiaopu.xiaopuMobi.dto.admin.topics;

import com.chinaxiaopu.xiaopuMobi.model.BaseEntity;
import lombok.Data;

/**
 * Created by Administrator on 2016/11/7.
 */
@Data
public class TopicsListDto extends BaseEntity {
    private Integer id;
    private Integer userId;
    private String userName;
    private String userImages;
    private String realName;
    private Integer type;
    private Integer tipOffCount;
    private String createTime;
    private String tagName;
    private String challengeType;
    private String channelName;
    private String isOver;
    private String isSuccess;
    private Integer commentCnt;
    private String allCount;//组合
    private Integer visitSum;//参战数
    private Integer allVotes;//投票数
    private Integer isOfficial;//显示类型 ：0:非官方；1:官方
    private Integer orderType; //排序类型 1：参战数排序 2：投票数排序 3：评论数排序
    private Integer isChallenge;
    private Integer channelId;
    private String startTime;
    private String endTime;
    private Integer over;
    private Integer recommend;
    private String takeTime;
    private String slogan;
    private Integer sort;

}
