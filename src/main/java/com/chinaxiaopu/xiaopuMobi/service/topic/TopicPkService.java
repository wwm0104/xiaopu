package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.dto.topic.PkIdDto;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.PkVote;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.VoteResultListVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.VoteResultVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.VoteRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Wang on 2016/11/2.
 */
@Service
public class TopicPkService {

    @Autowired
    private TopicPkMapper topicPkMapper;
    @Autowired
    private PkVoteResultMapper pkVoteResultMapper;
    @Autowired
    private PkVoteMapper pkVoteMapper;

    /**
     * 投票规则
     * @param id
     * @return
     */
    public VoteRuleVo voteRule(int id) {
        VoteRuleVo voteRuleVo = topicPkMapper.voteRule(id);
        return voteRuleVo;
    }

    public VoteResultVo voteList(PkIdDto pkIdDto,UserInfo userInfo) {
        VoteResultVo voteResultVo = new VoteResultVo();
//        if (!StringUtils.isEmpty(pkIdDto.getPage()) && !StringUtils.isEmpty(pkIdDto.getRows())) {
//            PageHelper.startPage(pkIdDto.getPage(), pkIdDto.getRows());
//        }
        List<VoteResultListVo> voteResultListVos =  pkVoteResultMapper.voteList(pkIdDto.getPkId());
        voteResultVo.setList(voteResultListVos);
        // 查询此用户投票
        if(!StringUtils.isEmpty(userInfo)){
            PkVote pkVote = pkVoteMapper.selectByPkIdAndUserId(pkIdDto.getPkId(), userInfo.getUserId());
            if(!StringUtils.isEmpty(pkVote)){
                voteResultVo.setTopicId(pkVote.getTopicId());
            }
        }

        return voteResultVo;
    }
}
