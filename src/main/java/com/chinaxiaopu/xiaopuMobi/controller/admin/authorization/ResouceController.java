package com.chinaxiaopu.xiaopuMobi.controller.admin.authorization;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.ResourceDto;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.RoleResourceDto;
import com.chinaxiaopu.xiaopuMobi.model.Resource;
import com.chinaxiaopu.xiaopuMobi.service.authorization.ResouceService;
import com.chinaxiaopu.xiaopuMobi.service.shiro.ShiroFilterChainDefinitionsService;
import com.chinaxiaopu.xiaopuMobi.vo.authorization.ResourceTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellie on 2016/11/17.
 */
@Slf4j
@Controller
@RequestMapping("/resource")
public class ResouceController extends ContextController {

    @Autowired
    private ShiroFilterChainDefinitionsService shiroFilterChainDefinitionsService;

    @Autowired
    private ResouceService resouceService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String resourceManage(){
        return "authorization/resource/index";
    }

    @RequestMapping(value = "/findResource/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    public List<ResourceTreeVo> findResource(@PathVariable("roleId") int roleId) {
        List<Resource> _resources = resouceService.findByRoleId(roleId);
        List<ResourceTreeVo> treeList = new ArrayList<>();
        for (Resource resource : _resources) {
            ResourceTreeVo treeVo = new ResourceTreeVo();
            treeVo.setId(resource.getId());
            if (resource.getParentId() == 0) {
                treeVo.setParent("#");
            } else {
                treeVo.setParent(resource.getParentId() + "");
            }
            treeVo.setSelected(true);
            treeVo.setText(resource.getName());
            treeVo.setSort(Integer.parseInt(resource.getSort()));
            treeList.add(treeVo);

        }
        return treeList;
    }

    @RequestMapping(value = "/findResource", method = RequestMethod.POST)
    @ResponseBody
    public List<ResourceTreeVo> findResource(@RequestBody RoleResourceDto roleResourceDto) {
        List<Resource> WholeResource = resouceService.findAll();
        List<ResourceTreeVo> treeVoList = new ArrayList<>();
        if (("超级管理员").equals(roleResourceDto.getRoleName())) {
            treeVoList = resouceService.buildAminResourceTreeData(WholeResource);
            return treeVoList;
        }
        List<Resource> userResources = resouceService.findByRoleId(roleResourceDto.getRoleId());
        treeVoList = resouceService.builUserResourceTreeData(userResources, WholeResource);
        return treeVoList;
    }

    @RequestMapping(value = "/findResourceTable", method = RequestMethod.POST)
    @ResponseBody
    public Result findResourceTable() {
        Result result = new Result<>();
        try {
            List<Resource> adminResource = resouceService.selectResource();
            result.setObj(adminResource);
            return result;
        } catch (Exception e) {
            result.setMsg("资源树创建失败");
        }

        return result;
    }

    /**
     * 创建资源
     *
     * @param resource
     * @return
     */
    @RequestMapping(value = "/createResource", method = RequestMethod.POST)
    @ResponseBody
    public Result createResource(@RequestBody Resource resource) {
        Result result = new Result();
        try {
            int count = resouceService.createResource(resource, getCurrentId());
            if (count > 0) {
                shiroFilterChainDefinitionsService.updatePermission();
                result.setResultCode(Result.SUCCESS);
                result.setMsg("创建成功");
            } else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("创建失败");
            }
        } catch (Exception e) {
            log.error("创建失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg("创建失败");
        }
        return result;
    }

    @RequestMapping(value = "/updateRoleResource", method = RequestMethod.POST)
    @ResponseBody
    public Result updateResource(@RequestBody RoleResourceDto roleResourceDto) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(roleResourceDto)) {
                result.setResultCode(Result.FAILURE);
                result.setMsg("角色/资源Id不可为空");
            }
            int count = resouceService.updateRoleResource(roleResourceDto);
            if (count > 0) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("资源更新成功");
                return result;
            } else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("资源更新失败");
                return result;
            }
        } catch (Exception e) {
            log.error("资源更新失败");
            result.setResultCode(Result.FAILURE);
            result.setMsg("资源更新失败");
        }
        return result;
    }

    @RequestMapping(value = "/selectAll", method = RequestMethod.POST)
    @ResponseBody
    public Result selectAll() {
        Result result = new Result();
        try {
            List<Resource> resources = resouceService.findAll();
            if (resources != null) {
                result.setObj(resources);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error("", e);
            result.setResultCode(Result.FAILURE);
        }

        return result;
    }

    @RequestMapping(value = "/findResourceByResourceId/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result findResourceByResourceId(@PathVariable("id") int id) {
        Result<Resource> result = new Result();
        try {
            Resource resource = resouceService.findResourceById(id);
            result.setObj(resource);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("获取资源信息失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/updateResource", method = RequestMethod.POST)
    @ResponseBody
    public Result updateResource(@RequestBody ResourceDto resourceDto) {
        Result<Resource> result = new Result();
        Resource resource = new Resource();
        try {
            resource.setId(resourceDto.getId());
            resource.setName(resourceDto.getName());
            resource.setUrl(resourceDto.getUrl());
            resource.setUpdateBy(getCurrentId());
            resource.setType(resourceDto.getType());
            resource.setSort(resourceDto.getSort());
            int count = resouceService.updateResource(resource);
            if (count > 0) {
                shiroFilterChainDefinitionsService.updatePermission();
                result.setObj(resource);
                result.setMsg("资源更新成功");
                result.setResultCode(Result.SUCCESS);
                return result;
            } else {
                result.setMsg("资源更新失败");
                result.setResultCode(Result.FAILURE);
                return result;
            }
        } catch (Exception e) {
            log.error("资源更新失败", e);
            result.setMsg("资源更新失败");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/deleteResource/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteResource(@PathVariable("id") int id) throws Exception {
        Result result = new Result();
        try {
            int count = resouceService.deleteResource(id);
            if (count > 0) {
                shiroFilterChainDefinitionsService.updatePermission();
                result.setMsg("删除成功");
                result.setResultCode(Result.SUCCESS);
                return result;
            } else {
                result.setMsg("删除失败");
                result.setResultCode(Result.FAILURE);
                return result;
            }
        } catch (Exception e) {
            log.error("删除失败", e);
            result.setMsg("删除失败");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


}
