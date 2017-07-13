package com.chinaxiaopu.xiaopuMobi.vo.topic;

import lombok.Data;

/**
 * 用户信息
 * Created by Administrator on 2016/11/4.
 */
@Data
public class UserPkVo {
    private Integer userId;
    private String avatars;
    private String userNickname;
    private Integer topicId;
    private Integer userSex;
    private Integer voteCnt;
}
