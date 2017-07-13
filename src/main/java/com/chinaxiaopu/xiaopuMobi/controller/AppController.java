package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.dto.AppGetPageUrlDto;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuwei
 * date: 2016/10/19
 */
@Log
@RestController
@RequestMapping("/app")
public class AppController {

    @ApiOperation(value = "app获取首页地址", notes = "app获取首页的地址")
    @RequestMapping(value = "/getPageUrl", method = RequestMethod.POST)
    public Result<AppGetPageUrlDto> getPageUrl(@RequestBody AppGetPageUrlDto appGetPageUrlDto) {
        Result<AppGetPageUrlDto> result = new Result<>();
        try {
            SystemConfig systemConfig = SystemConfig.getInstance();
            appGetPageUrlDto.setPageUrl(systemConfig.getPartnerIndex());
            result.setObj(appGetPageUrlDto);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            result.setResultCode(Result.FAILURE);
        }

        return result;
    }
    //
    @ApiOperation(value = "举报接口", notes = "app举报接口")
    @RequestMapping(value = "/authApi/reportPage", method = RequestMethod.POST)
    public Result<String> reportPage() {
        Result<String> result = new Result<>();
        try {
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }
}
