package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.dto.UserInfoListDto;
import com.chinaxiaopu.xiaopuMobi.model.Prize;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;

import java.util.List;

/**
 * Created by ycy on 2016/11/30.
 */
public class PrizesListVo extends Prize{
    private Integer pkCnt;
    private Integer voteCnt;
    private Integer surplusStock;
    private List<UserInfoListDto> rewardUser;


    public List<UserInfoListDto> getRewardUser() {
        return rewardUser;
    }

    public void setRewardUser(List<UserInfoListDto> rewardUser) {
        this.rewardUser = rewardUser;
    }

    public Integer getSurplusStock() {
        return surplusStock;
    }

    public void setSurplusStock(Integer surplusStock) {
        this.surplusStock = surplusStock;
    }

    public Integer getPkCnt() {
        return pkCnt;
    }

    public void setPkCnt(Integer pkCnt) {
        this.pkCnt = pkCnt;
    }

    public Integer getVoteCnt() {
        return voteCnt;
    }

    public void setVoteCnt(Integer voteCnt) {
        this.voteCnt = voteCnt;
    }
}
