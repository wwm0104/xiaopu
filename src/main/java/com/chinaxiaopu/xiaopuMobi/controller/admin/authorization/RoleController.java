package com.chinaxiaopu.xiaopuMobi.controller.admin.authorization;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.RolesDto;
import com.chinaxiaopu.xiaopuMobi.model.Role;
import com.chinaxiaopu.xiaopuMobi.model.RoleResource;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.RoleService;
import com.chinaxiaopu.xiaopuMobi.service.authorization.RoleResourceService;
import com.chinaxiaopu.xiaopuMobi.service.authorization.UserInfoService;
import com.chinaxiaopu.xiaopuMobi.service.authorization.UserRoleService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Ellie on 2016/11/16.
 */
@Slf4j
@Controller
@RequestMapping("/role")
public class RoleController extends ContextController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private UserInfoService userInfoService;


    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String RoleManage(){
        return "authorization/role/index";
    }
    /**
     * 跳转人员管理页面
     * @param role
     * @param model
     * @return
     */
    @RequestMapping(value = "/presonalManage", method = RequestMethod.GET)
    public String presonal(Role role, Model model) {
        List<UserInfo> userInfoList = userInfoService.findUserInfoListUnderRole(role.getId());
        role = roleService.findRoles(role.getId());
        model.addAttribute("userInfoList", userInfoList);
        model.addAttribute("role", role);
        return "authorization/role/presonalManage";
    }

    /**
     * 创建新角色
     *
     * @param rolesDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createRole", method = RequestMethod.POST)
    @ResponseBody
    public Result createRole(@RequestBody RolesDto rolesDto) throws Exception {
        Result result = roleService.addRoles(rolesDto);
        return result;
    }

    /**
     * 删除角色信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteRole/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteRole(@PathVariable("id") int id) {
        Result result = roleService.deleteRoles(id);
        return result;
    }


    /**
     * 更新角色信息
     *
     * @param rolesDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    @ResponseBody
    public Result updateRole(@RequestBody RolesDto rolesDto) throws Exception {
        Result<Role> result = new Result<>();
        Role role = new Role();
        if (rolesDto != null) {
            role.setId(rolesDto.getRoleId());
            role.setRoleKey(rolesDto.getRoleKey());
            role.setRoleName(rolesDto.getRoleName());
            role.setCreateTime(new Date());
            int flag = roleService.updateRolesInfo(role);
            if (flag > 0) {
                result.setMsg("success");
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setMsg("fail");
                result.setResultCode(Result.FAILURE);
            }
        } else {
            result.setMsg("fail");
            result.setResultCode(Result.FAILURE);
        }

        return result;
    }

    /**
     * 查询所有角色
     *
     * @param rolesDto
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<Role>> findAll(@RequestBody RolesDto rolesDto, Model model) throws Exception {
        Result<PageInfo<Role>> result = new Result<PageInfo<Role>>();
        PageInfo<Role> pageInfo = new PageInfo<Role>();
        try {
            pageInfo = roleService.findAll(rolesDto);
            result.setObj(pageInfo);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("获取角色列表失败", e);
            result.setResultCode(Result.FAILURE);
        }

        return result;
    }

    @RequestMapping(value = "/findRoleListByUserId/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public Result findRoleListByUserId(@PathVariable("userId") int userId) {
        Result result = new Result();
        try {
            List<Role> roleList = roleService.findRoleListByUserId(userId);
            if (roleList != null && roleList.size() > 0) {
                result.setObj(roleList);
                result.setResultCode(Result.SUCCESS);
                return result;
            } else {
                result.setResultCode(Result.FAILURE);
            }
        } catch (Exception e) {
            log.error("获取用户角色列表失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 获取角色列表
     *
     * @param rolesDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<Role>> getRoleList(@RequestBody RolesDto rolesDto) throws Exception {
        Result<PageInfo<Role>> result = new Result<PageInfo<Role>>();
        PageInfo<Role> rolePageInfo = new PageInfo<>();
        try {
            rolePageInfo = roleService.getRoleList(rolesDto);
            result.setObj(rolePageInfo);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/findRoleList", method = RequestMethod.POST)
    @ResponseBody
    public Result findRoleList() throws Exception {
        Result result = new Result();
        try {
            List<Role> roleList = roleService.selectAll();
            result.setObj(roleList);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/findRoleByRoleName", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<Role>> findRoleByRoleName(@RequestBody RolesDto rolesDto) throws Exception {
        Result<PageInfo<Role>> result = new Result<PageInfo<Role>>();
        PageInfo<Role> pageInfo = new PageInfo<Role>();
        try {
            pageInfo = roleService.findRoleByRolesDto(rolesDto);
            result.setObj(pageInfo);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("获取角色列表失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 查找选中用户信息
     *
     * @param roleId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findRole/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    public Result findRole(@PathVariable("roleId") int roleId, Model model) throws Exception {
        Result result = new Result<PageInfo<Role>>();
        try {
            Role role = roleService.findRoles(roleId);
            result.setObj(role);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("获取角色列表失败", e);
            result.setResultCode(Result.FAILURE);
        }

        return result;
    }

    /**
     * 给用户添加角色
     *
     * @param rolesDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addRoleByUserId", method = RequestMethod.POST)
    @ResponseBody
    public Result addRoleByUserId(@RequestBody RolesDto rolesDto) throws Exception {
        Result result = new Result();
        if (rolesDto.getUserId() == null) {
            result.setMsg("请指定对应的用户");
            result.setResultCode(Result.FAILURE);
        }
        int flag = userRoleService.createUserRole(rolesDto);
        if (flag > 0) {
            result.setResultCode(Result.SUCCESS);
        } else {
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 给当前角色添加资源
     *
     * @param roleResource
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addResources", method = RequestMethod.POST)
    @ResponseBody
    public Result addResources(@RequestBody RoleResource roleResource) throws Exception {
        Result result = new Result();
        if (roleResource.getResourceId() == 0 || roleResource.getRoleId() == 0) {
            result.setResultCode(Result.FAILURE);
            return result;
        }
        List<RoleResource> _roleResource = roleResourceService.selectByRoleId(roleResource.getRoleId());
        for (RoleResource resource : _roleResource) {
            if (resource.getResourceId() == roleResource.getResourceId()) {
                result.setResultCode(Result.FAILURE);
                result.setMsg("资源已存在无需重复添加");
                return result;
            }
        }
        roleResourceService.addRoleResource(roleResource);
        result.setResultCode(Result.SUCCESS);
        return result;
    }
}
