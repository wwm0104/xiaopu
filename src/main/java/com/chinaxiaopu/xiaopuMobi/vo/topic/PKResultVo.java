package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.model.PkResult;
import com.chinaxiaopu.xiaopuMobi.model.PkVoteResult;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PKResultVo extends PkResult{
    @Setter @Getter
    private List<PkVoteResult> list;
}