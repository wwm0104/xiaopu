package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.PkVote;
import com.chinaxiaopu.xiaopuMobi.model.PkVoteExample;
import com.chinaxiaopu.xiaopuMobi.model.PkVoteKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PkVoteMapper {
    long countByExample(PkVoteExample example);

    int deleteByExample(PkVoteExample example);

    int deleteByPrimaryKey(PkVoteKey key);

    int insert(PkVote record);

    int insertSelective(PkVote record);

    List<PkVote> selectByExample(PkVoteExample example);

    PkVote selectByPrimaryKey(PkVoteKey key);

    int updateByExampleSelective(@Param("record") PkVote record, @Param("example") PkVoteExample example);

    int updateByExample(@Param("record") PkVote record, @Param("example") PkVoteExample example);

    int updateByPrimaryKeySelective(PkVote record);

    int updateByPrimaryKey(PkVote record);

    int selectCountByPkIdAndUserId(@Param("pkId")Integer pkId, @Param("userId")Integer userId);

    PkVote selectByPkIdAndUserId(@Param("pkId")Integer pkId, @Param("userId")Integer userId);
}