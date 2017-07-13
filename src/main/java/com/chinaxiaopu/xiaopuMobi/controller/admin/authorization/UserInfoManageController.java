package com.chinaxiaopu.xiaopuMobi.controller.admin.authorization;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.RolesDto;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.UserCreateDto;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.UserInfoSelectDto;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.UserRoleDto;
import com.chinaxiaopu.xiaopuMobi.model.Role;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.RoleService;
import com.chinaxiaopu.xiaopuMobi.service.authorization.UserInfoService;
import com.chinaxiaopu.xiaopuMobi.service.authorization.UserRoleService;
import com.chinaxiaopu.xiaopuMobi.vo.authorization.UserRoleVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ellie on 2016/12/2.
 */
@Slf4j
@Controller
@RequestMapping("/userInfo/manage")
public class UserInfoManageController extends ContextController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String userManege() {
        return "authorization/user/index";
    }

    /**
     * 跳转用户详情页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/userDetails", method = RequestMethod.GET)
    public String userDetails(Model model) throws Exception {
        int userId = getCurrentId();
        UserInfo e = userInfoService.findUserInfoByUserId(userId);
        if (e != null) {
            model.addAttribute("e", e);
            model.addAttribute("path", getImgsHostDomain());
        }
        return "authorization/user/userDetails";
    }

    /**
     * 查询所有用户列表
     *
     * @param userInfoSelectDto
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<UserRoleVo>> findAll(@RequestBody UserInfoSelectDto userInfoSelectDto) {
        Result<PageInfo<UserRoleVo>> result = new Result<>();
        try {
            log.debug("POST请求  获取所有用户");
            PageInfo<UserRoleVo> userRoleVoPageInfo = userInfoService.findAll(userInfoSelectDto);
            result.setObj(userRoleVoPageInfo);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("获取所有用户失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 根据userId查询用户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/findUserInfoById/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public Result<UserRoleVo> findUserInfoById(@PathVariable("userId") int userId) {
        Result<UserRoleVo> result = new Result<>();
        try {
            log.debug("POST请求 获取当前用户信息");
            UserRoleVo UserRoleVo = userInfoService.findUserInfoByUserId(userId);
            if (UserRoleVo != null) {
                result.setObj(UserRoleVo);
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
        } catch (Exception e) {
            log.error("获取当前用户信息失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 获取用户信息列表
     *
     * @param userInfoSelectDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getUserInfoList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<UserRoleVo>> getUserInfoList(@RequestBody UserInfoSelectDto userInfoSelectDto) {
        Result<PageInfo<UserRoleVo>> result = new Result<>();
        PageInfo<UserRoleVo> userRoleVoPageInfo = new PageInfo<>();
        try {
            log.debug("POST请求  获取用户列表");
            userRoleVoPageInfo = userInfoService.getUserInfoList(userInfoSelectDto);
            result.setObj(userRoleVoPageInfo);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("获取用户列表失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 查询对应角色下的用户
     *
     * @param rolesDto
     * @return
     */
    @RequestMapping(value = "/findRoleUserInfoList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<UserInfo>> findRoleUserInfoList(@RequestBody RolesDto rolesDto) {
        Result<PageInfo<UserInfo>> result = new Result<>();
        PageInfo<UserInfo> userInfoPageInfo = new PageInfo<>();
        try {
            log.debug("POST请求  获取用户信息列表");
            if (!StringUtils.isEmpty(rolesDto.getRoleId())) {
                userInfoPageInfo = userInfoService.findUserInfoListUnderRole(rolesDto);
                result.setObj(userInfoPageInfo);
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setMsg("角色id不可为空");
                result.setResultCode(Result.FAILURE);
            }
            return result;
        } catch (Exception e) {
            log.error("获取用户信息列表失败", e);
            result.setResultCode(Result.FAILURE);
        }

        return result;

    }

    /**
     * 查询不在该角色下的用户
     *
     * @param userInfoSelectDto
     * @return
     */
    @RequestMapping(value = "/findUserNotInRole", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<UserInfo>> findUserNotInRole(@RequestBody UserInfoSelectDto userInfoSelectDto) {
        Result<PageInfo<UserInfo>> result = new Result<>();
        PageInfo<UserInfo> userInfoPageInfo = new PageInfo<>();
        try {
            log.debug("POST请求  获取用户信息列表");
            if (!StringUtils.isEmpty(userInfoSelectDto.getRoleId())) {
                userInfoPageInfo = userInfoService.findUserNotInRole(userInfoSelectDto);
                result.setObj(userInfoPageInfo);
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setMsg("角色id不可为空");
                result.setResultCode(Result.FAILURE);
            }
            return result;
        } catch (Exception e) {
            log.error("获取用户信息列表失败", e);
            result.setResultCode(Result.FAILURE);
        }

        return result;

    }

    /**
     * 根据userId查询角色信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findUserRoleInfo/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result findUserRoleInfo(@PathVariable("id") int id) {
        Result result = new Result();
        try {
            log.debug("POST请求  获取用户角色");
            List<Role> roleList = roleService.selectRoleByUserId(id);
            result.setObj(roleList);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求 获取用户角色失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    /**
     * 给用户赋权限（用户角色信息）
     *
     * @param userRoleDto
     * @return
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUserInfo(@RequestBody UserRoleDto userRoleDto) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(userRoleDto.getUserInfo().getUserId())) {
                result.setMsg("用户id不可为空");
                result.setResultCode(Result.FAILURE);
            } else {
                int flag = userInfoService.updateUserInfo(userRoleDto.getUserInfo());
                if (flag > 0) {
                    if (userRoleDto.getRoleIdList() != null && userRoleDto.getRoleIdList().size() > 0) {
                        userRoleService.updateUserRoleInfo(userRoleDto);
                    }
                }
                result.setMsg("用户信息更新成功");
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (Exception e) {
            result.setMsg("用户信息更新失败");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 创建系统用户
     *
     * @param userCreateDto
     * @return
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public Result createUser(@RequestBody UserCreateDto userCreateDto) {
        Result result = new Result();
        try {
            log.debug("POST请求  后台添加用户");
            result = userInfoService.createUser(userCreateDto);
        } catch (Exception e) {
            log.error("后台添加用户失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    /**
     * 删除用户角色的关联关系
     *
     * @param rolesDto
     * @return
     */
    @RequestMapping(value = "/deleteUserRole", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteUserRole(@RequestBody RolesDto rolesDto) {
        Result result = new Result();
        try {
            log.debug("POST请求  逻辑删除角色下的用户");
            int count = userRoleService.deleteUserRole(rolesDto);
            if (count > 0) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
            return result;
        } catch (Exception e) {
            log.error("删除失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }
}
