package com.chinaxiaopu.xiaopuMobi.controller.admin;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.AppVersionDto;
import com.chinaxiaopu.xiaopuMobi.model.AppVersion;
import com.chinaxiaopu.xiaopuMobi.service.AppVersionService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by xiaohao on 2016/10/25.
 */
@Slf4j
@Controller
@RequestMapping("/admin/appVersion")
public class AppVersionComtroller {
    @Autowired
    private AppVersionService appVersionService;

    /**
     * 跳转版本信息列表
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String initList(){
        return "version/list";
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<AppVersion>> getList(@RequestBody AppVersionDto appVersionDto) {
        Result<PageInfo<AppVersion>> result=new Result<PageInfo<AppVersion>>();
        PageInfo<AppVersion> appVersionPageInfo=new PageInfo<AppVersion>();
        try {
            log.debug("post请求 版本列表");
            appVersionPageInfo= appVersionService.getList(appVersionDto);
            result.setObj(appVersionPageInfo);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e){
            log.error("post请求 版本列表失败",e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/getById",method = RequestMethod.POST)
    @ResponseBody
    public AppVersion getById(Integer id) {
        AppVersion appVersion=new AppVersion();
        try {
            appVersion=appVersionService.getById(id);//根据id获取版本信息方法
        }catch (Exception e){
            e.printStackTrace();
        }
        return appVersion;
    }

    /**
     *添加版本信息
     * @param appVersion
     * @return
     */

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestBody AppVersion appVersion) {
        Result result = new Result();
        try {
            appVersion=appVersionService.byAppIdorUseragent(appVersion);//根据id找到对应的name
            boolean ishas=appVersionService.add(appVersion);
            if(ishas) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("添加成功");
            }else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("添加失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setResultCode(Result.FAILURE);
            result.setMsg("添加失败");
        }
        return result;
    }

    /**
     * 修改版本信息
     * @param appVersion
     * @return
     */
    @ApiOperation(value = "修改版本信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody AppVersion appVersion)
    {
        Result result=new Result();
        try {
            appVersion=appVersionService.byAppIdorUseragent(appVersion);//根据id找到对应的name
            boolean ishas=appVersionService.update(appVersion);
            if(ishas) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("修改成功");
            }else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("修改失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据id删除版本信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ResponseBody
    public Result del(Integer id)
    {
        Result result=new Result();
        try {
            boolean ishas=appVersionService.del(id);//根据id删除删除版本信息方法
            if(ishas) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("删除成功");
            }else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("删除失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
