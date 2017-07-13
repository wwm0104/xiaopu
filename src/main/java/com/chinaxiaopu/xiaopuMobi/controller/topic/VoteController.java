package com.chinaxiaopu.xiaopuMobi.controller.topic;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicIdDto;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicService;
import com.chinaxiaopu.xiaopuMobi.service.topic.VoteService;
import com.chinaxiaopu.xiaopuMobi.vo.topic.LatestTopicVo;
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
@RequestMapping("/vote")
public class VoteController extends ContextController{

    @Autowired
    private TopicService topicService;
    @Autowired
    private VoteService voteService;

    @ApiOperation(value = "top5投票内容获取--Wang", notes = "top5投票内容获取\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/top5", method = RequestMethod.POST)
    public Result<List<LatestTopicVo>> VoteTop5() {
        Result<List<LatestTopicVo>> result = new Result<>();
        try {
            log.debug("top5投票内容获取");
            List<LatestTopicVo> topicVoList = topicService.VoteTop5();
            result.setObj(topicVoList);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("top5投票内容获取 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "用户投票--Wang", notes = "用户投票\n" +
            "返回值：0代表失败，1代表成功，42代表已投票")
    @RequestMapping(value = "/authApi/create", method = RequestMethod.POST)
    public Result create(@RequestBody TopicIdDto topicIdDto) {
        Result result = new Result();
        try {
            log.debug("用户投票");
            UserInfo userInfo = getCurrentUser(topicIdDto);
            int resultCode = voteService.createVote(topicIdDto,userInfo);
            if(resultCode==1){
                result.setResultCode(Result.SUCCESS);
            }else if(resultCode==0){
                result.setMsg("已投票");
                result.setResultCode(Constants.HAVE_TO_VOTE);
            }
        } catch (Exception e) {
            log.error("用户投票 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

}