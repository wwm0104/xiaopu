package com.chinaxiaopu.xiaopuMobi.controller.admin;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.AdminListDto;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.AdminUserInfoService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiaohao on 2016/10/28.
 */
@Slf4j
@Controller
@RequestMapping("/admin/AdminUserInfo")
public class UserInfoController {
    @Autowired
    private AdminUserInfoService adminUserInfoService;

    /**
     * 跳转至管理员列表界面
     * @return
     */
    @RequestMapping("/")
    public String Index()
    {
        return "admin/adminList";
    }

    /**
     * 获取管理员列表
     * @param adminListDto
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Result<PageInfo<UserInfo>> getAdminList(@RequestBody AdminListDto adminListDto) {
        Result<PageInfo<UserInfo>> result=new Result<PageInfo<UserInfo>>();
        PageInfo<UserInfo> pageInfo=new PageInfo<UserInfo>();
        try {

            log.debug("Post请求 获取管理员列表");
            pageInfo= adminUserInfoService.getAdminList(adminListDto);
            result.setObj(pageInfo);
            result.setResultCode(Result.SUCCESS);
            return result;
        }catch (Exception e){
            log.error("获取管理员列表失败",e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    /**
     *跳转添加管理员界面
     * @return
     */
    @RequestMapping("/toAdd")
    public String add(){
        return  "admin/createAdmin";
    }

    /**
     * 添加管理员
     * @param adminListDto
     * @return
     */
    @RequestMapping("/doAdd")
    @ResponseBody
    public Result doAdd(@RequestBody AdminListDto adminListDto){
        Result result=new Result();
        try {
            log.debug("添加管理员");
            return adminUserInfoService.add(adminListDto);
        }catch (Exception e){
            log.error("添加管理员失败",e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return  result;
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public Result del(Integer id){
        Result result=new Result();
        try {
            return adminUserInfoService.del(id);
        }catch (Exception e){
            log.error("删除管理员失败",e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }
}
