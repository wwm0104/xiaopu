package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.RolesDto;
import com.chinaxiaopu.xiaopuMobi.mapper.RoleMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.RoleResourceMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserRoleMapper;
import com.chinaxiaopu.xiaopuMobi.model.Role;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by liuwei
 * date: 16/10/17
 */
@Service
public class RoleService extends AbstractService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    public List<Role> selectRoleByUserId(final Integer userId) {
        return roleMapper.selectRoleByUserId(userId);
    }


    /**
     * 添加角色
     *
     * @param rolesDto
     * @return
     */
    public Result addRoles(RolesDto rolesDto) {
        Result result = new Result();
        if (rolesDto != null) {
            Role role = new Role();
            role.setRoleName(rolesDto.getRoleName());
            role.setUpdateTime(new Date());
            role.setId(rolesDto.getRoleId());
            role.setCreateBy(1);
            role.setCreateTime(new Date());
            role.setRoleKey(rolesDto.getRoleKey());
            role.setAvailable(1);
            roleMapper.insert(role);
            result.setMsg("角色添加成功");
            result.setResultCode(Result.SUCCESS);
        } else {
            result.setMsg("角色未定义");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 查询角色信息
     *
     * @return
     */
    public Role findRoles(Integer id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        return role;
    }

    /**
     * 根据用户id查询用户角色列表
     *
     * @param userId
     * @return
     */
    public List<Role> findRoleListByUserId(Integer userId) {
        List<Role> roleList = roleMapper.selectRoleByUserId(userId);
        return roleList;
    }

    public List<Role> selectAll() {
        List<Role> roleList = roleMapper.findAll();
        return roleList;
    }

    /**
     * 更新角色信息
     *
     * @return
     */
    public int updateRolesInfo(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 删除角色信息
     *
     * @return
     */
    public Result deleteRoles(Integer id) {
        Result result = new Result();
        try {
            roleResourceMapper.deleteRoleResource(id);
            userRoleMapper.deleteByRoleId(id);
            roleMapper.deleteByPrimaryKey(id);
            result.setResultCode(Result.SUCCESS);
            result.setMsg("delete  success");
        } catch (Exception e) {
            result.setMsg("delete  fail");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    public PageInfo<Role> findAll(RolesDto rolesDto) {
        if (!StringUtils.isEmpty(rolesDto.getPage()) && !StringUtils.isEmpty(rolesDto.getRows())) {
            PageHelper.startPage(rolesDto.getPage(), rolesDto.getRows());
        }
        List<Role> roleList = roleMapper.findAll();
        PageInfo<Role> rolePageInfo = new PageInfo<>(roleList);
        return rolePageInfo;
    }

    public PageInfo<Role> findRoleByRolesDto(RolesDto rolesDto) {
        if (!StringUtils.isEmpty(rolesDto.getPage()) && !StringUtils.isEmpty(rolesDto.getRows())) {
            PageHelper.startPage(rolesDto.getPage(), rolesDto.getRows());
        }
        List<Role> roleList = roleMapper.selectRoleByRoleDto(rolesDto.getRoleName());
        PageInfo<Role> rolePageInfo = new PageInfo<>(roleList);
        return rolePageInfo;
    }

    /**
     * 根据关键字查询角色列表
     *
     * @param rolesDto
     * @return
     */
    public PageInfo<Role> getRoleList(RolesDto rolesDto) {
        Role role = new Role();
        //分页
        if (!StringUtils.isEmpty(rolesDto.getPage())) {
            role.setPage(rolesDto.getPage());
        }
        if (!StringUtils.isEmpty(rolesDto.getRows())) {
            role.setRows(rolesDto.getRows());
        }

        if (role.getPage() != null && role.getRows() != null) {
            PageHelper.startPage(role.getPage(), role.getRows());//设置起始位置
        }
        List<Role> roleList = roleMapper.selectRoleByKeyWord(rolesDto);
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);

        return pageInfo;
    }

}
