package com.chinaxiaopu.xiaopuMobi.vo.topic;

import lombok.Data;

/**
 * 不服榜
 * Created by Administrator on 2016/11/2.
 */
@Data
public class UserRankVo {
    private Integer userId;//用户id
    private String userNickname;//昵称
    private String userAvatarUrl;//头像
    private Integer voteCnt;//参与数
    private String path;
    private Integer row;
    private Integer userSex;
}
