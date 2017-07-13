package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.EventUIDto;
import com.chinaxiaopu.xiaopuMobi.dto.EventUserMsgDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.EventUserMsgService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ycy on 2016/11/14.
 */
@Slf4j
@Controller
public class EventUserMsgController extends ContextController {

    @Autowired
    private EventUserMsgService eventUserMsgService;

    @MessageMapping("/sendEventMsg")
    @SendTo("/topic/acceptMsg")
    public String sendMsg(String msgObj) {
        log.info("send to server msg:"+msgObj);
        Result result = new Result();
        Gson gson = new Gson();
        EventUserMsgDto eventUserMsgDto = gson.fromJson(msgObj,EventUserMsgDto.class);
        try {
            log.debug("POST请求 活动报名");
            UserInfo current = getCurrentUser(eventUserMsgDto);
            eventUserMsgDto.setUserId(current.getUserId());
            eventUserMsgDto.setUserName(current.getNickName());
            eventUserMsgDto.setAvatarUrl(current.getAvatarUrl());
            eventUserMsgService.sendMsg(eventUserMsgDto);


            result.setResultCode(Result.SUCCESS);
            result.setObj(eventUserMsgDto);
            result.setPath(SystemConfig.getInstance().getImgsDomain());

        } catch (UnknownLoginException ue) {
            log.error("活动报名失败-用户未登录");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("POST请求 活动报名失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        String resultStr = new Gson().toJson(result,Result.class);
        log.info("-->"+resultStr);
        return resultStr;
    }
}
