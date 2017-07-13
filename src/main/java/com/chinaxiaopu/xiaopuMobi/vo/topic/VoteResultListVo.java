package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.vo.ContextVo;
import lombok.Getter;
import lombok.Setter;
/**
 * Created by Wang on 2016/11/7.
 */
public class VoteResultListVo extends ContextVo{
    @Setter @Getter
    private Integer topicId;
    @Setter @Getter
    private Integer creatorId;
    @Setter @Getter
    private String avatarUrl;
    @Setter @Getter
    private String nickName;
    @Setter @Getter
    private Integer voteCnt;

}