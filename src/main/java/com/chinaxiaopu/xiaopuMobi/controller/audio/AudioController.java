package com.chinaxiaopu.xiaopuMobi.controller.audio;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.audio.*;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.audio.AudioService;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicDownService;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.audio.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 音频Controller
 * Created by wuning on 2016/12/5.
 */
@Slf4j
@RestController
@RequestMapping("/audio")
public class AudioController extends ContextController {

    @Autowired
    private AudioService audioService;
    @Autowired
    private TopicDownService topicDownService;


    @ApiOperation(value = "音频频道列表-武宁", notes = "音频频道列表\n" +
            "返回值：0代表失败，1代表成功;")
    @RequestMapping(value = "/channel/list", method = RequestMethod.POST)
    public Result<List<AudioChannelListVo>> channelList() {
        Result<List<AudioChannelListVo>> result = new Result<>();
        try {
            log.debug("POST请求  音频频道列表");
            List<AudioChannelListVo> list = audioService.findVoiceChannelList();
            result.setObj(list);
            result.setResultCode(Result.SUCCESS);
            if (StrUtils.isEmpty(list)) {
                result.setMsg(Result.NO_DATA);
            }
            return result;
        } catch (Exception e) {
            log.error("POST请求  音频频道列表 FAIL", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    @ApiOperation(value = "音频置顶轮播列表-武宁", notes = "音频置顶轮播列表\n" +
            "返回值：0代表失败，1代表成功;")
    @RequestMapping(value = "/top/list", method = RequestMethod.POST)
    public Result<List<TopListVo>> topList() {
        Result<List<TopListVo>> result = new Result<>();
        try {
            log.debug("POST请求  音频置顶轮播列表");
            List<TopListVo> list = audioService.findTopList();
            result.setObj(list);
            result.setResultCode(Result.SUCCESS);
            if (StrUtils.isEmpty(list)) {
                result.setMsg(Result.NO_DATA);
            }
            return result;
        } catch (Exception e) {
            log.error("POST请求  音频置顶轮播列表 FAIL", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }



    @ApiOperation(value = "音频贴推荐列表-武宁", notes = "音频贴推荐列表\n" +
            "返回值：0代表失败，1代表成功;")
    @RequestMapping(value = "/recomment/list", method = RequestMethod.POST)
    public Result<List<TopListVo>> recommentList() {
        Result<List<TopListVo>> result = new Result<>();
        try {
            log.debug("POST请求 音频贴推荐列表");
            List<TopListVo> list = audioService.findRecommentList();
            result.setObj(list);
            result.setResultCode(Result.SUCCESS);
            if (StrUtils.isEmpty(list)) {
                result.setMsg(Result.NO_DATA);
            }
            return result;
        } catch (Exception e) {
            log.error("POST请求  音频贴推荐列表 FAIL", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 101:取消订阅成功
     * 102:订阅成功
     * 0 :失败
     * -1 ：未登录
     * @param audioSubscribeDto
     * @return
     */
    @ApiOperation(value = "订阅/取消订阅-武宁", notes = "订阅\n" +
            "返回值：0代表失败，1代表成功;")
    @RequestMapping(value = "/authApi/subscribe", method = RequestMethod.POST)
    public Result subscribe(@RequestBody AudioSubscribeDto audioSubscribeDto) {
        Result result = new Result<>();
        try {

            log.debug("POST请求 订阅/取消订阅");
            if (StrUtils.isEmpty(audioSubscribeDto.getChannelId())) {  //音频频道 为空判断
                result.setResultCode(Result.FAILURE);
                result.setMsg("音频频道ID不能为空");
                return result;
            }
            UserInfo userInfo = getCurrentUser(audioSubscribeDto);
            if (userInfo != null) {
                if (StrUtils.isNotEmpty(userInfo.getUserId())) {
                    int row = audioService.subscribe(userInfo.getUserId(), audioSubscribeDto.getChannelId());
                    switch (row) {
                        case 102:
                            result.setResultCode(102);
                            result.setMsg("订阅成功");
                            return result;
                        case 101:
                            result.setResultCode(101);
                            result.setMsg("取消订阅成功");
                            return result;
                    }
                } else {
                    result.setResultCode(Constants.UNKNOWN_LOGIN);
                    result.setMsg(Result.NOT_LOGGED_IN);
                    return result;
                }
            }
        } catch (UnknownLoginException ue) {
            log.error("订阅失败->用户未登录");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
            result.setMsg(Result.NOT_LOGGED_IN);
        } catch (Exception e) {
            log.error("POST请求  订阅/取消订阅 FAIL", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "订阅音频频道列表--Wang", notes = "订阅音频频道列表\n" +
            "返回值：0代表失败，1代表成功;")
    @RequestMapping(value = "/authApi/subscribe/list", method = RequestMethod.POST)
    public Result<SubscribeListVo> subscribeList(@RequestBody SubscribeDto subscribeDto) {
        Result<SubscribeListVo> result = new Result<>();
        SubscribeListVo subscribeListVo = new SubscribeListVo();
        try {
            log.debug("POST请求 订阅音频频道列表");
            UserInfo userInfo = getCurrentUser(subscribeDto);
            PageInfo<SubscribeVo> pageInfo = audioService.getSubscribeChannelList(subscribeDto,userInfo);
            if (subscribeDto.getPage() > pageInfo.getPages()) {
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            } else {
                subscribeListVo.setList(pageInfo.getList());
                subscribeListVo.setTimePoint(subscribeDto.getTimePoint());
                result.setObj(subscribeListVo);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (UnknownLoginException ue) {
            log.error("订阅音频频道列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("POST请求  订阅音频频道列表 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "频道音频列表--Wang", notes = "频道音频列表\n" +
            "返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/channel/audio/list", method = RequestMethod.POST)
    public Result<AudioListVo> channelAudioList(@RequestBody ChannelDto channelDto) {
        Result<AudioListVo> result = new Result<>();
        AudioListVo audioListVo = new AudioListVo();
        try {
            log.debug("POST请求 频道音频列表");
            UserInfo userInfo;
            try {
                userInfo = getCurrentUser(channelDto);
            } catch (UnknownLoginException ule) {
                userInfo = null;
            }
            if(!StringUtils.isEmpty(userInfo)){
                channelDto.setUserId(userInfo.getUserId());
            }
            PageInfo<AudioVo> pageInfo = audioService.getChannelAudioList(channelDto);
            if (channelDto.getPage() > pageInfo.getPages()) {
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            } else {
                audioListVo.setAudioCnt(new Long(pageInfo.getTotal()).intValue());
                audioListVo.setList(pageInfo.getList());
                audioListVo.setTimePoint(channelDto.getTimePoint());
                result.setObj(audioListVo);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error("POST请求  频道音频列表 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "音频搜索列表--Wang", notes = "音频搜索列表\n" +
            "返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/search/list", method = RequestMethod.POST)
    public Result<AudioListVo> searchAudioList(@RequestBody AudioSearchDto audioSearchDto) {
        Result<AudioListVo> result = new Result<>();
        AudioListVo audioListVo = new AudioListVo();
        try {
            log.debug("POST请求 音频搜索列表");
            UserInfo userInfo;
            try {
                userInfo = getCurrentUser(audioSearchDto);
            } catch (UnknownLoginException ule) {
                userInfo = null;
            }
            if(!StringUtils.isEmpty(userInfo)){
                audioSearchDto.setUserId(userInfo.getUserId());
            }
            PageInfo<AudioVo> pageInfo = audioService.getSearchAudioList(audioSearchDto);
            if (audioSearchDto.getPage() > pageInfo.getPages()) {
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            } else {
                audioListVo.setList(pageInfo.getList());
                audioListVo.setTimePoint(audioSearchDto.getTimePoint());
                result.setObj(audioListVo);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error("POST请求  音频搜索列表 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "频道详情--Wang", notes = "频道详情\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/channel/info", method = RequestMethod.POST)
    public Result<ChannelVo> channelInfo(@RequestBody ChannelDto channelDto) {
        Result<ChannelVo> result = new Result<>();
        try {
            log.debug("POST请求 频道详情");
            UserInfo userInfo;
            try {
                userInfo = getCurrentUser(channelDto);
            } catch (UnknownLoginException ule) {
                userInfo = null;
            }
            ChannelVo channelVo = audioService.channelInfo(channelDto,userInfo);
            result.setObj(channelVo);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求  频道详情 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "音频详情--Wang", notes = "音频详情\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public Result<AudioInfoVo> audioInfo(@RequestBody AudioDto audioDto) {
        Result<AudioInfoVo> result = new Result<>();
        try {
            log.debug("POST请求 音频详情");
            UserInfo userInfo;
            try {
                userInfo = getCurrentUser(audioDto);
            } catch (UnknownLoginException ule) {
                userInfo = null;
            }
            AudioInfoVo audioInfoVo = audioService.audioInfo(audioDto,userInfo);
            result.setObj(audioInfoVo);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求  音频详情 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "播放音频播放量增加-王运豪", notes = "播放音频播放量增加\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/clickAudio/{id}",method = RequestMethod.POST)
    public Result clickAudio(@PathVariable final int id){
        Result result=new Result();
        try {
            log.debug("POST请求 播放音频播放量增加");
            boolean ishas=topicDownService.updatePlayCntByTopicId(id);
            if(ishas){
                result.setMsg("成功");
                result.setResultCode(Result.SUCCESS);
            }else{
                result.setMsg("失败");
                result.setResultCode(Result.FAILURE);
            }
        }catch (Exception e){
            log.error("POST请求  播放音频播放量增加", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }

        return result;
    }

}
