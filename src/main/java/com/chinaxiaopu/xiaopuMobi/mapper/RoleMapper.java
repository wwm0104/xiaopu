package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.authorization.RolesDto;
import com.chinaxiaopu.xiaopuMobi.model.Role;
import com.chinaxiaopu.xiaopuMobi.model.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleByUserId(@Param("userId") final Integer userId);

    List<Role> findAll();

    @Select("select * from roles where role_name = #{roleName ,jdbcType = VARCHAR}")
    Role selectByRoleName(String roleName);

    @Select("select * from roles where role_name = #{roleName ,jdbcType = VARCHAR}")
    List<Role> selectRoleByRoleDto(String roleName);

    List<Role> selectRoleByKeyWord(RolesDto rolesDto);
}