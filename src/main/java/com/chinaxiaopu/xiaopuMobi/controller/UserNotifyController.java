package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.UserNotifyDto;
import com.chinaxiaopu.xiaopuMobi.dto.UserUIDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.UserNotifyService;
import com.chinaxiaopu.xiaopuMobi.vo.UserNotifyListVo;
import com.chinaxiaopu.xiaopuMobi.vo.UserNotifyVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuwei
 * date: 2016/12/7
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserNotifyController extends ContextController {

    @Autowired
    UserNotifyService userNotifyService;

    @ApiOperation(value = "用户消息列表", notes = "用户消息列表分页查询,必须登录\n " +
            "isRead状态 0: 未读,1:已读")
    @RequestMapping(value = "/authApi/notifyList", method = RequestMethod.POST)
    public Result<UserNotifyListVo> notifyList(@RequestBody UserNotifyDto userNotifyDto) throws Exception {
        Result result = new Result();
        UserNotifyListVo userNotifyListVo = new UserNotifyListVo();
        try {
            UserInfo current = getCurrentUser(userNotifyDto);
            PageInfo<UserNotifyVo> pageInfo = userNotifyService.selectByUser(userNotifyDto,current);
            if (userNotifyDto.getPage() > pageInfo.getPages()) {
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            } else {
                userNotifyListVo.setTimePoint(userNotifyDto.getTimePoint());
                userNotifyListVo.setList(pageInfo.getList());
                result.setObj(userNotifyListVo);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (UnknownLoginException ue) {
            log.error("用户消息列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户消息列表:",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "修改用户未读消息", notes = "修改用户未读消息")
    @RequestMapping(value = "/authApi/notifyRead", method = RequestMethod.POST)
    public Result notifyRead(@RequestBody UserUIDto userUIDto) throws Exception {
        Result result = new Result();
        try {
            UserInfo current = getCurrentUser(userUIDto);
            userNotifyService.notifyRead(current.getUserId());
            result.setResultCode(Result.SUCCESS);
        } catch (UnknownLoginException ue) {
            log.error("用户消息列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户消息列表:",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }
}
