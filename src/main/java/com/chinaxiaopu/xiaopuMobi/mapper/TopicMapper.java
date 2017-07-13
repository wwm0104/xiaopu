package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.DeleteMyTopicDto;
import com.chinaxiaopu.xiaopuMobi.dto.MyTopicParam;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.ChannelListDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicListDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicsListDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.AudioConfirmDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PKResultDetailDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicByPrizeDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicListDtos;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicSearchDto;
import com.chinaxiaopu.xiaopuMobi.model.Prize;
import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.chinaxiaopu.xiaopuMobi.model.TopicExample;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.TopicDetaVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.TopicDetailVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.TopicListVo;
import com.chinaxiaopu.xiaopuMobi.vo.audio.AudioDetailVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TopicMapper {
    long countByExample(TopicExample example);

    int deleteByExample(TopicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Topic record);

    int insertSelective(Topic record);

    List<Topic> selectByExample(TopicExample example);

    Topic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Topic record, @Param("example") TopicExample example);

    int updateByExample(@Param("record") Topic record, @Param("example") TopicExample example);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKey(Topic record);

    @Select("select count(1) from topics where creator_id=#{userId,jdbcType=INTEGER}")
    int selectCountByUserId(@Param("userId") Integer userId);   //查询我的图文总数量

    /**
     * 根据图文标题搜索列表
     *
     * @param topicSearchDto
     * @return
     */
    List<TopicSearchVo> getSearchList(TopicSearchDto topicSearchDto);

    /**
     * 以后要从topic_ranking中获取数据
     */
    List<TopicVo> hotList(Integer userId);

    /**
     * 以后要从topic_ranking中获取数据
     */
    List<TopicVo> newList(TopicListDtos topicListDtos);

    /**
     * 根据奖品id查询图文列表
     *
     * @param topicByPrizeDto
     * @return
     */
    List<TopicVo> topicListByPrizeId(TopicByPrizeDto topicByPrizeDto);

    List<LatestTopicVo> VoteTop5();
    List<LatestTopicVo> VoteTop5_recommend();

    List<TopicVo> voteContext(Integer challengeTopicId);

    List<TopicVo> voteContextMovie(Integer challengeTopicId);

    List<Topic> selectUerTopic(MyTopicParam myTopicParam);

    int updateMyTopic(DeleteMyTopicDto deleteMyTopicDto);

    /**
     * 获取PK结果基础数据
     *
     * @return
     */
    List<PKResultDetailVo> getPkDetail(PKResultDetailDto pKResultDto);

    int updateChallengeTopicIdAsId(Integer id);

    int isAttend(@Param("pkId") Integer pkId, @Param("userId") Integer userId);

    Topic selectTopicById(Integer topicId);

    /**
     * 后台 ：查询 图文视频列表
     *
     * @return
     */
    List<TopicsListDto> selectTopicsList(TopicsListDto topicsListDto);

    /**
     * 查询 频道列表
     *
     * @return
     */
    List<ChannelListDto> channelList(ChannelListDto channelListDto);

    int deleteTopic(Integer id);

    /**
     * 后台 ：查询 图文视频列表(需要审核的主题)
     */
    List<TopicListVo> topicList(TopicListDto topicListDto);

    /**
     * 后台 ：查询主题详情
     */
    TopicDetailVo topicDetail(int id);

    Integer selectPkCntByUserId(Integer creatorId);

    Integer selectWinCntByUserId(Integer creatorId);

    /**
     * 更新原有的数据
     *
     * @param topic
     * @return
     */
    int updateOldrecommend(Topic topic);

    /**
     * 查询主题详情
     *
     * @param topicId
     * @return
     */
    TopicDetaVo selectTopicsDetail(Integer topicId);

    /**
     * 查询主题详情
     *
     * @param topicId
     * @return
     */
    TopicDetaVo selectTopicsDetail1(Integer topicId);

    int selectUserIdByTopicId(Integer topicId);

    int selectWaitReviewTopicCount();

    /**
     * 修改评论数
     *
     * @param id
     * @return
     */
    int updateCommentCnt(Integer id);

    int selectIsChallenge(DeleteMyTopicDto deleteMyTopicDto);

    @Update("update topics set expire_time=now() where challenge_topic_id = (select topic_id from topic_pk where id =#{pkId})")
    int updateExpireTime(@Param("pkId") final int pkId);

    /**
     * 查询主页主题推荐列表
     *
     * @return
     */
    List<TopicIndexVo> getIndexList();

    /**
     * 通过头像点击查询图文信息
     *
     * @param myTopicParam
     * @return
     */
    List<Topic> selectTopicsByUserId(MyTopicParam myTopicParam);

    /**
     * 根据主题创建人Id 查询用户社团
     *
     * @param creatorId
     * @return
     */
    TopicDetaVo selectTopicsGroupById(Integer creatorId);

    /**
     * 根据主题ID 查询标签
     *
     * @param id
     * @return
     */
    TopicDetaVo selectTopicsTagById(Integer id);

    /**
     * 查询主题的所有挑战人数
     *
     * @param topicId
     * @return
     */
    Integer selectChallergeCount(Integer topicId);

    /**
     * 查询主题投票数
     *
     * @param topicId
     * @return
     */
    TopicDetaVo selectVoteCnt(Integer topicId);

    /**
     * 查询音频列表(主播)
     *
     * @return
     */
    List<Topic> selectAudioList(Topic topic);


    List<AudioDetailVo> selectAudioList1(AudioConfirmDto audioConfirmDto);

    int updateIsDeleteById(Integer id);

    /**
     * 查询最新的某个图文视频
     *
     * @return
     */
    TopicVo latestTopic(@Param("topicId") Integer topicId, @Param("userId") Integer userId);

    /**
     * 根据 主贴ID 查询奖品信息
     *
     * @param topicId
     * @return
     */
    List<Prize> selectPrizeByTopicId(@Param("topicId") Integer topicId);

    /**
     * 根据主贴id查询审核状态
     * @param topicId
     * @return
     */
    @Select("select `status` from topics where id=#{topicId}")
    int selectStatusByTopicId(@Param("topicId") Integer topicId);
}