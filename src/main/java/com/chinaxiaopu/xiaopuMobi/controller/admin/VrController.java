package com.chinaxiaopu.xiaopuMobi.controller.admin;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.VrDto;
import com.chinaxiaopu.xiaopuMobi.service.UserVrActivityService;
import com.chinaxiaopu.xiaopuMobi.vo.VrUserVo;
import com.github.pagehelper.PageInfo;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiaohao on 2016/11/15.
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class VrController {
    @Autowired
    private UserVrActivityService userVrActivityService;

    @RequestMapping("/vrList")
    public String toVrList(){
        return "vr/vrList";
    }
    @RequestMapping(value = "/getVrList",method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<VrUserVo>> getVrList(@RequestBody VrDto vrDto){
        Result<PageInfo<VrUserVo>> result=new Result<>();
        try {
            result.setObj(userVrActivityService.selectVrList(vrDto));
            result.setResultCode(Result.SUCCESS);
        }catch (Exception e){
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }
}
