package com.chinaxiaopu.xiaopuMobi.service.audio;

import com.chinaxiaopu.xiaopuMobi.dto.audio.AudioDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.AudioSearchDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.ChannelDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.SubscribeDto;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.UserChannelSubscribe;
import com.chinaxiaopu.xiaopuMobi.model.UserChannelSubscribeExample;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.audio.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 音频 service
 * Created by wuning on 2016/12/5.
 */
@Slf4j
@Service
public class AudioService {
    @Autowired
    private TopicPlayMapper topicPlayMapper; //播放次数表
    @Autowired
    private UserChannelSubscribeMapper userChannelSubscribeMapper; //订阅表
    @Autowired
    private UserFansMapper userFansMapper; //粉丝
    @Autowired
    private UserFocusAnchorMapper userFocusAnchorMapper;//关注
    @Autowired
    private PkChannelMapper pkChannelMapper;
    @Autowired
    private ChannelAnchorMapper channelAnchorMapper;


    /**
     * 音频频道列表 ：接口
     *
     * @return
     */
    public List<AudioChannelListVo> findVoiceChannelList() {
        List<AudioChannelListVo> list = pkChannelMapper.findVoiceChannelList();
        return list;
    }

    /**
     * 音频置顶轮播列表
     *
     * @return
     */
    public List<TopListVo> findTopList() {
        List<TopListVo> list = pkChannelMapper.findTopList();
        if (StrUtils.isNotEmpty(list)) {
            for (TopListVo vo : list) {
                vo.setContentMap(vo.getContent());
                vo.setContent("");
            }

        }
        return list;
    }



    /**
     * 音频贴推荐列表
     *
     * @return
     */
    public List<TopListVo> findRecommentList() {
        List<TopListVo> list = pkChannelMapper.findRecommentList();
        if (StrUtils.isNotEmpty(list)) {
            for (TopListVo vo : list) {
                vo.setContentMap(vo.getContent());
                vo.setContent("");
            }

        }
        return list;
    }

    /**
     * 订阅 /取消订阅
     *
     * @param userId
     * @param channelId
     * @return
     */
    public int subscribe(Integer userId, Integer channelId) {
        int row = 0;
        UserChannelSubscribeExample example = new UserChannelSubscribeExample();
        example.createCriteria().andChannelIdEqualTo(channelId).andUserIdEqualTo(userId);
        long count = userChannelSubscribeMapper.countByExample(example);
        if (count > 0) {//删除
            UserChannelSubscribeExample deleteExample = new UserChannelSubscribeExample();
            deleteExample.createCriteria().andChannelIdEqualTo(channelId).andUserIdEqualTo(userId);
           int deleteCount =  userChannelSubscribeMapper.deleteByExample(deleteExample);
           if(deleteCount>0){
               row =101; // 取消订阅成功
           }else{
               row =0; //系统错误
           }
        } else if (count == 0) {//添加
            UserChannelSubscribe e = new UserChannelSubscribe();
            e.setChannelId(channelId);
            e.setUserId(userId);
            int insertRow = userChannelSubscribeMapper.insertSelective(e);
            if(insertRow>0){
                row =102; //订阅成功
            }else{
                row =0; //系统错误
            }
        }
        return  row;
    }

    /**
     * 订阅的音频频道列表
     * Wang
     * @param subscribeDto,userInfo
     * @return
     */
    public PageInfo<SubscribeVo> getSubscribeChannelList(SubscribeDto subscribeDto,UserInfo userInfo) {
        if (!StringUtils.isEmpty(subscribeDto.getPage()) && !StringUtils.isEmpty(subscribeDto.getRows())) {
            PageHelper.startPage(subscribeDto.getPage(), subscribeDto.getRows());
        }
        List<SubscribeVo> subscribeVoList =  userChannelSubscribeMapper.getSubscribeChannelList(userInfo.getUserId(),subscribeDto.getTimePoint());
        PageInfo<SubscribeVo> pageInfo = new PageInfo<>(subscribeVoList);
        return pageInfo;
    }

    /**
     * 频道音频列表
     * Wang
     * @param channelDto
     * @return
     */
    public PageInfo<AudioVo> getChannelAudioList(ChannelDto channelDto) {
        if (!StringUtils.isEmpty(channelDto.getPage()) && !StringUtils.isEmpty(channelDto.getRows())) {
            PageHelper.startPage(channelDto.getPage(), channelDto.getRows());
        }
        List<AudioVo> audioVoList = pkChannelMapper.getChannelAudioList(channelDto);
        PageInfo<AudioVo> pageInfo = new PageInfo<>(audioVoList);
        return pageInfo;
    }

    /**
     * 音频搜索列表
     * Wang
     * @param audioSearchDto
     * @return
     */
    public PageInfo<AudioVo> getSearchAudioList(AudioSearchDto audioSearchDto) {
        if (!StringUtils.isEmpty(audioSearchDto.getPage()) && !StringUtils.isEmpty(audioSearchDto.getRows())) {
            PageHelper.startPage(audioSearchDto.getPage(), audioSearchDto.getRows());
        }
        List<AudioVo> audioVoList = pkChannelMapper.getSearchAudioList(audioSearchDto);
        PageInfo<AudioVo> pageInfo = new PageInfo<>(audioVoList);
        return pageInfo;
    }

    /**
     * 频道详情
     * Wang
     * @param channelDto,userInfo
     * @return
     */
    public ChannelVo channelInfo(ChannelDto channelDto, UserInfo userInfo) {
        if(!StringUtils.isEmpty(userInfo)){
            channelDto.setUserId(userInfo.getUserId());
        }
        ChannelVo channelVo = pkChannelMapper.channelInfo(channelDto);
        List<AnchorVo> anchorVoList = channelAnchorMapper.getChannelAnchorList(channelDto);
        channelVo.setAnchorList(anchorVoList);

        return channelVo;
    }

    /**
     * 音频详情
     * Wang
     * @param audioDto
     * @param userInfo
     * @return
     */
    public AudioInfoVo audioInfo(AudioDto audioDto, UserInfo userInfo) {
        if(!StringUtils.isEmpty(userInfo)){
            audioDto.setUserId(userInfo.getUserId());
        }
        AudioInfoVo audioInfoVo = pkChannelMapper.audioInfo(audioDto);

        return audioInfoVo;
    }
}
