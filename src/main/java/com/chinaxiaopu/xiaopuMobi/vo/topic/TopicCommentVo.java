package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.model.TopicComment;
import com.chinaxiaopu.xiaopuMobi.util.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Wang on 2016/11/22.
 */
public class TopicCommentVo extends TopicComment{
    @Setter @Getter
    private Integer commentCnt;

    private String showTime;

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = DateTimeUtil.getShowTime(showTime);
    }

}