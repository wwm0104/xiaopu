package com.chinaxiaopu.xiaopuMobi.service.authorization;

import com.chinaxiaopu.xiaopuMobi.mapper.RoleResourceMapper;
import com.chinaxiaopu.xiaopuMobi.model.RoleResource;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Ellie on 2016/11/25.
 */
@Service
public class RoleResourceService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    public int insertBySelective(RoleResource roleResource) {
        return roleResourceMapper.insertSelective(roleResource);
    }


    public List<RoleResource> selectByRoleId(int roleId) {
        return roleResourceMapper.selectByRoleId(roleId);
    }


    public int addRoleResource(RoleResource roleResource) {
        return roleResourceMapper.insert(roleResource);
    }

    public int deleteRoleResourcesByResourceId(int resourceId) {
        return roleResourceMapper.deleteRoleResourceByResourceId(resourceId);

    }

    /**
     * 删除resourceIdList对应的角色关系
     *
     * @param resourceIds
     */
    public int deleteRoleResourceByResourceIds(List<Integer> resourceIds) {
        if (resourceIds != null) {
            return roleResourceMapper.deleteRoleResourceByResourceIds(resourceIds);
        }
        return 0;
    }

    /**
     * 根据角色id删除角色资源对应关系
     *
     * @param roleId
     */
    public int deleteRoleResources(int roleId) {
        return roleResourceMapper.deleteRoleResource(roleId);

    }

    /**
     * 更新资源角色
     *
     * @param roleId
     * @param resourceIdSet
     * @return
     */
    public int updateRoleResources(Integer roleId, Set<Integer> resourceIdSet) {
        int flag = 0;
        List<Integer> resourceIdList = new ArrayList<>();
        resourceIdList.addAll(resourceIdSet);
        List<RoleResource> roleResources = new ArrayList<>();
        for (Integer id : resourceIdList) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(id);
            roleResources.add(roleResource);
        }
        if (StrUtils.isNotEmpty(roleResources)) {
            flag = roleResourceMapper.insertRoleResourceList(roleResources);
        }
        return flag;
    }

}