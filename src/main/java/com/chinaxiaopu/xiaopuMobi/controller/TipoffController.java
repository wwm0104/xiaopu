package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.TipoffDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicDownService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiaohao on 2016/11/18.
 */
@Slf4j
@RestController
@RequestMapping("/tipoff")
public class TipoffController extends ContextController {
    @Autowired
    private TopicDownService topicDownService;

    @ApiOperation(value = "图文、活动、社团举报-王运豪", notes = "图文、活动、社团举报\n" +
            "接收值：tipoffId+举报类型tipoffType(1：帖子 2：活动 3：社团)+举报内容desc+登录凭证\t" + "返回值：0代表失败，1代表成功，-1没有登录")
    @RequestMapping(value = "/authApi/tipoff", method = RequestMethod.POST)
    public Result tipoff(@RequestBody TipoffDto tipoffDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 图文、活动、社团举报操作");
            //获取登录用户信息
            UserInfo userInfo = getCurrentUser(tipoffDto);
            //举报操作
            return topicDownService.doTipoff(tipoffDto, userInfo);
        } catch (UnknownLoginException ue) {
            log.error("用户举报失败->用户未登录");
            result.setResultCode(Constants.UNKNOWN_LOGIN);//-1:未登录
            result.setMsg(Result.NOT_LOGGED_IN);
        } catch (Exception e) {
            log.error("POST请求 用户举报失败", e);
            result.setResultCode(Result.FAILURE); //0:失败
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }
}
