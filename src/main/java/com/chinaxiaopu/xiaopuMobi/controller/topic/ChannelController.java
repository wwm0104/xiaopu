package com.chinaxiaopu.xiaopuMobi.controller.topic;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicPkDto;
import com.chinaxiaopu.xiaopuMobi.model.PkChannel;
import com.chinaxiaopu.xiaopuMobi.service.topic.FindChannelService;
import com.chinaxiaopu.xiaopuMobi.service.topic.PkFinishPrizeService;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicsCreateService;
import com.chinaxiaopu.xiaopuMobi.vo.topic.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wang on 2016/11/2.
 */

@Slf4j
@RestController
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    public TopicsCreateService topicsCreateService;
    @Autowired
    private FindChannelService findChannelService;

    @Autowired
    private PkFinishPrizeService pkFinishPrizeService;

    @ApiOperation(value = "获取频道列表-乐传阳", notes = "获取频道列表\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list() {
        Result<List<PkChannel>> result = new Result<List<PkChannel>>();
        try {
            List<PkChannel> list = topicsCreateService.getPkChannelList();
            //pkFinishPrizeService.PkFinish(4);
            result.setObj(list);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("请求PK频道列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "频道投票汇总 -武宁", notes = "频道投票汇总\n" +
            "返回值：0代表失败，1代表成功;name : 频道名称；parentType ： 频道id  为-1时查询全部 ；voteCnt ： 主题数量")
    @RequestMapping(value = "/countList", method = RequestMethod.POST)
    public Result<List<FindChannelVo>> countList() {
        Result<List<FindChannelVo>> result = new Result<>();
        try {
            List<FindChannelVo> list = findChannelService.getFindChannelMenu();
            result.setObj(list);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            result.setMsg(Result.SERVER_EXCEPTION);
            log.error("请求频道投票汇总列表失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "按照频道分类查询投票列表-武宁", notes = "按照频道分类查询投票列表\n 参数 ： parentType 频道id  为空时获取全部" +
            "返回值：0代表失败，1代表成功;id : 主题id ; slogan : 主题 ；content ： 冗余字段 json 串；isOver ： （0：已结束 1：未结束）; toatle ： 参与人数 ； userList : 参加挑战贴的用户信息（userId;用户id" +
            " avatars ： 用户图片;userNickname：用户昵称;topicId：主题id）")
    @RequestMapping(value = "/topicPk/list", method = RequestMethod.POST)
    public Result<List<TopicPkVo>> topicPkList(@RequestBody TopicPkDto topicPkDto) {
        Result<List<TopicPkVo>> result = new Result<>();
        try {
            List<TopicPkVo> list = findChannelService.getChannelTopicList(topicPkDto); //基础信息
            PageInfo<TopicPkVo> pageInfo = new PageInfo<>(list);
            if (topicPkDto.getPage() > pageInfo.getPageNum()) {
                result.setResultCode(Constants.DATA_NO);
            } else {
                result.setObj(list);
                result.setResultCode(Result.SUCCESS);
            }
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            result.setMsg(Result.SERVER_EXCEPTION);
            log.error("按照频道分类查询投票列表失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    @ApiOperation(value = "首页热门频道列表-武宁", notes = "热门频道列表\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/hotChannel", method = RequestMethod.POST)
    public Result hotChannel() {
        Result<List<MoreChildChannelVo>> result = new Result<>();
        try {
            List<MoreChildChannelVo> list = findChannelService.hotChannel();
            result.setObj(list);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("首页热门频道列表 FAIL", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    @ApiOperation(value = "首页获取更多频道列表-武宁", notes = "首页获取更多频道列表\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/moreChannel", method = RequestMethod.POST)
    public Result moreChannel() {
        Result<List<MoreChannelVo>> result = new Result<>();
        try {
            List<MoreChannelVo> list = findChannelService.moreChannel();
            result.setObj(list);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("首页获取更多频道列表 FAIL", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

}