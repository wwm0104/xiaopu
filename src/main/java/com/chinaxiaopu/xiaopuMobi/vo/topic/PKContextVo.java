package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.vo.ContextVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * Created by Wang on 2016/11/7.
 */
public class PKContextVo extends ContextVo {
    @Setter @Getter
    private List<TopicVo> list;
    @Setter @Getter

    private TopicVo topicVo;
    @Setter @Getter
    private TopicVo challengeTopicVo;
    @Setter @Getter
    private Integer pkId;
    @Setter @Getter
    private Integer voteTopicId = 0;
}