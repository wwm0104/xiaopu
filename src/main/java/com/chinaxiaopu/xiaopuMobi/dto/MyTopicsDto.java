package com.chinaxiaopu.xiaopuMobi.dto;

import com.chinaxiaopu.xiaopuMobi.model.Topic;

/**
 * Created by ycy on 2016/11/2.
 */
public class MyTopicsDto extends Topic{

    private Integer ifLike;

    public Integer getIfLike() {
        return ifLike;
    }

    public void setIfLike(Integer ifLike) {
        this.ifLike = ifLike;
    }
}
