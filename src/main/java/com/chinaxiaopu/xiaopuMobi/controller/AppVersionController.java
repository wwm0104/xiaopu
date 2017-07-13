package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.AppDto;
import com.chinaxiaopu.xiaopuMobi.model.AppVersion;
import com.chinaxiaopu.xiaopuMobi.service.AppVersionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuwei
 * date: 2016/10/19
 */
@Slf4j
@RestController
@RequestMapping("/appVersion")
public class AppVersionController extends ContextController{

    @Autowired
    AppVersionService appVersionService;

    @ApiOperation(value = "app获取最新版本接口", notes = "app获取最新版本接口,isUpdate 1: 强制更新,0:不强制")
    @RequestMapping(value = "/lastAppVersion", method = RequestMethod.POST)
    public Result<AppVersion> lastAppVersion(@RequestBody AppDto appDto) {
        Result<AppVersion> result = new Result<AppVersion>();
        try {
            AppVersion appVersion =  appVersionService.selectLastVersionByAppId(appDto.getAppId());
            result.setObj(appVersion);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {

            result.setMsg("获取版本信息失败");
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }



}
