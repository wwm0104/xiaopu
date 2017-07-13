package com.chinaxiaopu.xiaopuMobi.dto;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;

/**
 * Created by ycy on 2016/12/6.
 */
public class UserInfoListDto extends UserInfo {
    private String slogan;

    private Integer voteCnt;

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public Integer getVoteCnt() {
        return voteCnt;
    }

    public void setVoteCnt(Integer voteCnt) {
        this.voteCnt = voteCnt;
    }
}
