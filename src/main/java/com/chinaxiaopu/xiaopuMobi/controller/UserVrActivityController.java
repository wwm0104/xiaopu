package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.UserUIDto;
import com.chinaxiaopu.xiaopuMobi.dto.UserVrActivityDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.UserVrActivityService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * Created by xiaohao on 10/9/16.
 */
@Slf4j
@RestController
@RequestMapping("/vrActivity")
public class UserVrActivityController extends ContextController{

    @Autowired
    private UserVrActivityService userVrActivityService;

    @ApiOperation(value = "用户报名参加VR活动初始化上下文", notes = "用户报名参加VR活动初始化上下文,必须传入登录参数")
    @RequestMapping(value="/authApi/initJoin",method = RequestMethod.POST)
    public Result initJoin(@RequestBody UserUIDto userUIDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 初始化VR活动报名");
            UserInfo current = getCurrentUser(userUIDto);
            result.setObj(userVrActivityService.initVrActivity(current));
            result.setResultCode(Result.SUCCESS);
        } catch (UnknownLoginException ue) {
            log.error("初始化VR活动报名失败-用户未登录");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("POST请求 初始化VR活动报名失败", e);
            result.setMsg("服务器繁忙");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "用户报名参加VR活动", notes = "用户报名参加VR活动,必须传入登录参数。" +
            "appointmentDate:报名日期,appointmentTime:报名时间段,activityCnt:报名次数")
    @RequestMapping(value="/authApi/join",method = RequestMethod.POST)
    public Result join(@RequestBody UserVrActivityDto userVrActivityDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 VR活动报名");
            Integer cnt = userVrActivityDto.getActivityCnt();
            if (cnt<=0){
                result.setResultCode(Result.FAILURE);
                result.setMsg("报名人次不能低1人次");
                return result;
            }
            UserInfo current = getCurrentUser(userVrActivityDto);
            return userVrActivityService.joinVrActivity(userVrActivityDto,current);
        } catch (UnknownLoginException ue) {
            log.error("VR活动报名失败-用户未登录");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("POST请求 VR活动报名失败", e);
            result.setMsg("VR报名失败,服务器繁忙");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "根据日期获取该天所有时间段可预约次数", notes = "根据日期获取该天所有时间段可预约次数,必须传入登录参数。" +
            "appointmentDate:报名日期")
    @RequestMapping(value="/authApi/getTimeCntBydate",method = RequestMethod.POST)
    public Result getTimeCntBydate(@RequestBody UserVrActivityDto userVrActivityDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 VR活动报名");
            UserInfo current = getCurrentUser(userVrActivityDto);
            result.setResultCode(Result.SUCCESS);
            result.setObj(userVrActivityService.getVrActivityBydate(userVrActivityDto.getAppointmentDate()));
        } catch (UnknownLoginException ue) {
            log.error("根据日期获取该天所有时间段可预约次数失败-用户未登录");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("POST请求 根据日期获取该天所有时间段可预约次数失败", e);
            result.setMsg("服务器繁忙");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }
}