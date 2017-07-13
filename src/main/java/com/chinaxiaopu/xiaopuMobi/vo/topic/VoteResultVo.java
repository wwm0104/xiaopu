package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.vo.ContextVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * Created by Wang on 2016/11/7.
 */
public class VoteResultVo extends ContextVo{
    @Setter @Getter
    private Integer topicId = 0;
    @Setter @Getter
    private List<VoteResultListVo> list;

}