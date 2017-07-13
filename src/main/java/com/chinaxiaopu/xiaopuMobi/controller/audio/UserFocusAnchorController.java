package com.chinaxiaopu.xiaopuMobi.controller.audio;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.UserFocusAnchorDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.audio.UserFocusAnchorService;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.UserFocusVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Ellie on 2016/12/5.
 */
@Slf4j
@Controller
@RequestMapping("/audio")
public class UserFocusAnchorController extends ContextController {

    @Autowired
    private UserFocusAnchorService userFocusAnchorService;

    @ApiOperation(value = "关注主播", notes = "获取关注主播列表")
    @RequestMapping(value = "/authApi/getFocusList", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<UserFocusVo>> getFocusList(@RequestBody UserFocusAnchorDto userFocusAnchorDto) {
        Result<List<UserFocusVo>> result = new Result<>();
        UserInfo userInfo;
        try {
            log.debug("POST请求 获取关注主播列表");
            userInfo = getCurrentUser(userFocusAnchorDto);
            userFocusAnchorDto.setUserId(userInfo.getUserId());
            List<UserFocusVo> userFocusVoList = userFocusAnchorService.getUseraFocusAnchor(userFocusAnchorDto);
            PageInfo<UserFocusVo> pageInfo = new PageInfo<>(userFocusVoList);
            if (userFocusAnchorDto.getPage() > pageInfo.getPages()) {
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            } else {
                result.setObj(userFocusVoList);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (UnknownLoginException ue) {
            log.error("获取关注列表失败->用户未登录");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("POST请求 获取关注主播列表失败");
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }

        return result;
    }

    @ApiOperation(value = "关注或者取消关注", notes = "返回103表示取消关注成功，104表示关注成功，-1未登录状态，0失败")
    @RequestMapping(value = "/authApi/foucsOrCancelFocus", method = RequestMethod.POST)
    @ResponseBody
    public Result foucsOrCancelFocus(@RequestBody UserFocusAnchorDto userFocusAnchorDto) {
        Result result = new Result();
        UserInfo userInfo;
        try {
            log.debug("POST请求 关注/取消关注");
            if (StrUtils.isEmpty(userFocusAnchorDto.getFocusUserId())) {
                result.setResultCode(Result.FAILURE);
                result.setMsg("主播ID不能为空");
                return result;
            }
            userInfo = getCurrentUser(userFocusAnchorDto);
            if (userInfo != null) {
                userFocusAnchorDto.setUserId(userInfo.getUserId());
                int code = userFocusAnchorService.focusOrCancelFocus(userFocusAnchorDto);
                switch (code) {
                    case 103:
                        result.setResultCode(103);
                        result.setMsg("取消关注成功");
                        return result;
                    case 104:
                        result.setResultCode(104);
                        result.setMsg("关注成功");
                        return result;
                }
            } else {
                result.setResultCode(Constants.UNKNOWN_LOGIN);
                result.setMsg(Result.NOT_LOGGED_IN);
                return result;
            }
        } catch (UnknownLoginException ue) {
            log.error("关注失败->用户未登录");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);//-1未登录状态
        } catch (Exception e) {
            log.error("POST请求  关注/取消关注失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }
}
