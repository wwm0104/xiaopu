package com.chinaxiaopu.xiaopuMobi.controller.topic;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicIdDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicPkService;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicService;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PKContextVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.VoteRuleVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Wang on 2016/11/1.
 */

@Slf4j
@RestController
@RequestMapping("/topicPk")
public class TopicPkController extends ContextController{
    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicPkService topicPkService;

    @ApiOperation(value = "图文pk页面上下文--Wang", notes = "图文pk页面上下文\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/voteContext", method = RequestMethod.POST)
    public Result<PKContextVo> voteContext(@RequestBody TopicIdDto topicIdDto) {
        Result<PKContextVo> result = new Result<>();
        try {
            log.debug("POST请求 图文pk页面上下文");
            UserInfo userInfo;
            try {
                userInfo = getCurrentUser(topicIdDto);
            } catch (UnknownLoginException ule) {
                userInfo = null;
            }

            PKContextVo pkContextVo =  topicService.voteContext(topicIdDto,userInfo);
            result.setObj(pkContextVo);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e){
            log.error("POST请求  图文pk页面上下文失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "根据pkId获取投票规则详情--Wang", notes = "根据Pkid获取投票规则详情\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/voteRule/{id}", method = RequestMethod.POST)
    public Result<VoteRuleVo> voteRule(@PathVariable("id") final int id) {
        Result<VoteRuleVo> result = new Result<>();
        try {
            log.debug("POST请求 根据pkId获取投票规则详情");
            VoteRuleVo voteRuleVo = topicPkService.voteRule(id);
            result.setObj(voteRuleVo);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e){
            log.error("POST请求 根据pkId获取投票规则详情失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

}