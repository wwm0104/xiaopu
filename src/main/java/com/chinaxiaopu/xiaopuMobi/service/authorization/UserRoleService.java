package com.chinaxiaopu.xiaopuMobi.service.authorization;

import com.chinaxiaopu.xiaopuMobi.dto.authorization.RolesDto;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.UserRoleDto;
import com.chinaxiaopu.xiaopuMobi.mapper.RoleMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserRoleMapper;
import com.chinaxiaopu.xiaopuMobi.model.Role;
import com.chinaxiaopu.xiaopuMobi.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellie on 2016/11/16.
 */
@Service
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    public int createUserRole(RolesDto rolesDto) {

        Role role = roleMapper.selectByRoleName(rolesDto.getRoleName());
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(rolesDto.getUserId());
        return userRoleMapper.insert(userRole);
    }

    public int getRoleIdByUserId(Integer userId) {
        return userRoleMapper.selectRoleIdByUserId(userId);
    }

    /**
     * 批量插入用户角色
     *
     * @param userRoleDto
     * @return
     */
    public int updateUserRoleInfo(UserRoleDto userRoleDto) {
        int count = 0;
        int userId = userRoleDto.getUserInfo().getUserId();
        List<Integer> roleIdList = userRoleDto.getRoleIdList();
        List<UserRole> userRoleList = new ArrayList<>();
        for (int roleId : roleIdList) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userRoleDto.getUserInfo().getUserId());
            userRole.setRoleId(roleId);
            userRoleList.add(userRole);
        }
        List<UserRole> userRoles = userRoleMapper.selectUserRoleListByUserId(userId);
        if (userRoles != null && userRoles.size() > 0) {
            count = deleteUserRoleByUserId(userId);//清空用户原有角色列表
        }

        count = userRoleMapper.insertUserRoleList(userRoleList);
        return count;
    }

    public int deleteUserRole(RolesDto rolesDto) {
        return userRoleMapper.deleteById(rolesDto.getUserId(),rolesDto.getRoleId());
    }

    public int deleteUserRoleByUserId(Integer userId) {
        return userRoleMapper.deleteByPrimaryKey(userId);
    }
}
