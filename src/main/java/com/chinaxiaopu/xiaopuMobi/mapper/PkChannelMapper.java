package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.admin.channel.ChannelRecommend;
import com.chinaxiaopu.xiaopuMobi.dto.audio.AudioDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.AudioSearchDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.ChannelDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicPkDto;
import com.chinaxiaopu.xiaopuMobi.model.PkChannel;
import com.chinaxiaopu.xiaopuMobi.model.PkChannelExample;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.audio.*;
import com.chinaxiaopu.xiaopuMobi.vo.topic.*;
import org.apache.ibatis.annotations.Param;

public interface PkChannelMapper {
    long countByExample(PkChannelExample example);

    int deleteByExample(PkChannelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PkChannel record);

    int insertSelective(PkChannel record);

    List<PkChannel> selectByExample(PkChannelExample example);

    PkChannel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PkChannel record, @Param("example") PkChannelExample example);

    int updateByExample(@Param("record") PkChannel record, @Param("example") PkChannelExample example);

    int updateByPrimaryKeySelective(PkChannel record);

    int updateByPrimaryKey(PkChannel record);

    List<PkChannel> selectPkChannel();

    List<PkChannel> selectOfficalPkChannel();

    List<PkChannel> selectOfficalAudioPkChannel();

    /**
     * 频道投票汇总
     *
     * @return
     */
    List<FindChannelVo> getFindChannelMenu();

    /**
     * 按照频道分类查询投票列表
     *
     * @param topicPkDto
     * @return
     */
    List<TopicPkVo> getChannelTopicList(TopicPkDto topicPkDto);

    /**
     * 参加此次PK的所有用户
     *
     * @param
     * @return
     */
    List<UserPkVo> getChannelTopicUserList(Integer topicId);

    /**
     * 武宁  2016.12.1
     * 首页热门频道列表
     *
     * @return
     */
    List<MoreChildChannelVo> hotChannel();

    /**
     * 武宁 2016.12.1
     * 首页获取更多频道列表  父级LIST
     *
     * @return
     */
    List<MoreChannelVo> moreChannel();

    /**
     * 武宁 2016.12.1
     * 首页获取更多频道列表  子级LIST
     *
     * @return
     */
    List<MoreChildChannelVo> moreChildList();

    /**
     * 查询全部频道
     *
     * @return
     */
    List<PkChannel> selectAllPkChannel();

    /**
     * 音频频道列表 :接口
     *
     * @return
     */
    List<AudioChannelListVo> findVoiceChannelList();

    /**
     * 音频贴推荐列表
     *
     * @return
     */
    List<TopListVo> findRecommentList();

    /**
     * 频道音频列表
     *
     * @param channelDto
     * @return
     * @author Wang
     */
    List<AudioVo> getChannelAudioList(ChannelDto channelDto);

    /**
     * 搜索音频列表
     *
     * @param audioSearchDto
     * @return
     * @author Wang
     */
    List<AudioVo> getSearchAudioList(AudioSearchDto audioSearchDto);

    /**
     * 音频的频道信息
     *
     * @param channelDto
     * @return
     * @author Wang
     */
    ChannelVo channelInfo(ChannelDto channelDto);

    /**
     * 音频信息
     *
     * @param audioDto
     * @return
     * @author Wang
     */
    AudioInfoVo audioInfo(AudioDto audioDto);

    /**
     * 音频置顶轮播列表
     *
     * @return
     */
    List<TopListVo> findTopList();
}