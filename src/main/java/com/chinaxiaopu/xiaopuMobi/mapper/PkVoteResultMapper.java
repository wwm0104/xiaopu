package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.PkVoteResult;
import com.chinaxiaopu.xiaopuMobi.model.PkVoteResultExample;
import com.chinaxiaopu.xiaopuMobi.model.PkVoteResultKey;
import java.util.List;
import java.util.Map;

import com.chinaxiaopu.xiaopuMobi.vo.topic.VoteResultListVo;
import org.apache.ibatis.annotations.Param;

public interface PkVoteResultMapper {
    long countByExample(PkVoteResultExample example);

    int deleteByExample(PkVoteResultExample example);

    int deleteByPrimaryKey(PkVoteResultKey key);

    int insert(PkVoteResult record);

    int insertSelective(PkVoteResult record);

    List<PkVoteResult> selectByExample(PkVoteResultExample example);

    PkVoteResult selectByPrimaryKey(PkVoteResultKey key);

    int updateByExampleSelective(@Param("record") PkVoteResult record, @Param("example") PkVoteResultExample example);

    int updateByExample(@Param("record") PkVoteResult record, @Param("example") PkVoteResultExample example);

    int updateByPrimaryKeySelective(PkVoteResult record);

    int updateByPrimaryKey(PkVoteResult record);

    List<VoteResultListVo> voteList(Integer pkId);

    PkVoteResult selectByTopicId(Integer topicId);

    int updateByTopicId(PkVoteResult pkVoteResult);

    PkVoteResult selectVoteMax(Integer pkId);

    int selectCountByPkId(Integer pkId);

    int checkUserVote(Map<String,Object> map);

    int selectVoteCntByPkId(Integer pkId);

    List<Integer> selectUserIdList(Integer pkId);
}