package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.topic.AllChallengeDto;
import com.chinaxiaopu.xiaopuMobi.model.TopicPk;
import com.chinaxiaopu.xiaopuMobi.model.TopicPkExample;

import java.util.Date;
import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.PkTopicVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.AllTopickInSamePkVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.VoteRuleVo;
import org.apache.ibatis.annotations.Param;

public interface TopicPkMapper {
    long countByExample(TopicPkExample example);

    int deleteByExample(TopicPkExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TopicPk record);

    int insertSelective(TopicPk record);

    List<TopicPk> selectByExample(TopicPkExample example);

    TopicPk selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TopicPk record, @Param("example") TopicPkExample example);

    int updateByExample(@Param("record") TopicPk record, @Param("example") TopicPkExample example);

    int updateByPrimaryKeySelective(TopicPk record);

    int updateByPrimaryKey(TopicPk record);

    Integer selectPkIdByTopicId(Integer topicId);

    VoteRuleVo voteRule(int id);

    TopicPk selectByTopicId(Integer topicId);

    TopicPk selectByPkId(Integer pkId);

    int updateResultFinish(Integer pkId);

    int updateFinish(Integer pkId);

    List<AllTopickInSamePkVo> selectSomeInfoByPkId(AllChallengeDto allChallengeDto);

    String whoIsLeiZhu(Integer topicId);

    int selectCountBytopicId(Integer topicId);

    Date getEndTime(Integer topicId);

    List<PkTopicVo> selecAllPkId();
}