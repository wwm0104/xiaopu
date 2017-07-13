package com.chinaxiaopu.xiaopuMobi.dto.authorization;


import java.util.List;

/**
 * Created by Ellie on 2016/11/22.
 */
public class RoleResourceDto {

    private String roleName;
    private Integer roleId;
    private List<Integer> resourceList;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Integer> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Integer> resourceList) {
        this.resourceList = resourceList;
    }
}
