package com.chinaxiaopu.xiaopuMobi.controller.topic;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.topic.EventRankDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.UserRankDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.VotesRankDto;
import com.chinaxiaopu.xiaopuMobi.service.topic.EventRankService;
import com.chinaxiaopu.xiaopuMobi.vo.topic.EventRankVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.UserRankVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.VotesRankVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Wang on 2016/11/2.
 */

@Slf4j
@RestController
@RequestMapping("/ranking")
public class RankController {
    @Autowired
    private EventRankService eventRankService;

    @ApiOperation(value = "活动排行榜日，月，周榜-武宁", notes = "活动排行榜日，月，周榜\n  参数 ：（parentType  1 日 2周 3月）固定20条" +
            "返回值：0代表失败，1代表成功;row :序号; startTime :活动开始时间; endTime :活动结束时间 ;subject : 活动名称;groupId :所属社团id;groupName : 所属社团名称；eventId : 活动id;topicCnt :活动下主题数;posterImg :活动海报")
    @RequestMapping(value = "/event/list", method = RequestMethod.POST)
    public Result<List<EventRankVo>> eventList(@RequestBody EventRankDto eventRankDto) {
        Result<List<EventRankVo>> result = new Result<>();
        try {
            log.debug("POST请求 活动排行榜日，月，周榜");
            List<EventRankVo> list = eventRankService.getEventRankList(eventRankDto);
            result.setObj(list);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("POST请求  活动排行榜日，月，周榜", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "投票排行榜日，月，周榜-武宁", notes = "投票排行榜日，月，周榜\n  参数 ：（投票   parentType  1 日 2周 3月）固定20条" +
            "返回值：0代表失败，1代表成功 ;row : 排序 ；topicId : 投票主题id;voteCnt : 投票参与人数 ;slogan : 标题 ；expireTime ：截止时间；creatorNickname ： 发起者")
    @RequestMapping(value = "/vote/list", method = RequestMethod.POST)
    public Result<List<VotesRankVo>> voteList(@RequestBody VotesRankDto votesRankDto) {
        Result<List<VotesRankVo>> result = new Result<>();
        try {
            log.debug("POST请求 投票排行榜日，月，周榜");
            List<VotesRankVo> list = eventRankService.getVotesRankList(votesRankDto);
            result.setObj(list);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("POST请求  投票排行榜日，月，周榜", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "不服榜-武宁", notes = "不服榜\n  参数 ： type :1 " +
            "返回值：0代表失败，1代表成功;userId : 用户id;userNickname : 昵称；userAvatarUrl ： 头像；voteCnt ：参与数")
    @RequestMapping(value = "/userVote/joinList", method = RequestMethod.POST)
    public Result<List<UserRankVo>> joinList(@RequestBody UserRankDto userRankDto) {
        Result<List<UserRankVo>> result = new Result<>();
        try {
            log.debug("POST请求 不服榜");
            List<UserRankVo> list = eventRankService.getUserRankList(userRankDto);
            result.setObj(list);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("POST请求  不服榜", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "达人榜-武宁", notes = "达人榜\n   参数 ： type :2" +
            "返回值：0代表失败，1代表成功；userId : 用户id;userNickname : 昵称；userAvatarUrl ： 头像；voteCnt ：获胜数")
    @RequestMapping(value = "/userVote/talentList", method = RequestMethod.POST)
    public Result<List<UserRankVo>> talentList(@RequestBody UserRankDto userRankDto) {
        Result<List<UserRankVo>> result = new Result<>();
        try {
            log.debug("POST请求 达人榜");
            List<UserRankVo> list = eventRankService.getUserRankList(userRankDto);
            result.setObj(list);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("POST请求  达人榜", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

}