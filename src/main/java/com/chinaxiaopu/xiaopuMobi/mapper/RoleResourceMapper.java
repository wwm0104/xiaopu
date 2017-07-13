package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.RoleResource;
import com.chinaxiaopu.xiaopuMobi.model.RoleResourceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleResourceMapper {

    long countByExample(RoleResourceExample example);

    int deleteByExample(RoleResourceExample example);

    int deleteByPrimaryKey(RoleResource key);

    int insert(RoleResource record);

    int insertSelective(RoleResource record);

    int insertRoleResources(@Param("roleId") Integer roleId, @Param("resourceId") Integer resourceId);

    int insertRoleResourceList(List<RoleResource> roleResourceList);

    List<RoleResource> selectByExample(RoleResourceExample example);

    List<RoleResource> selectByRoleId(Integer roleId);

    int updateByExampleSelective(@Param("record") RoleResource record, @Param("example") RoleResourceExample example);

    int updateByExample(@Param("record") RoleResource record, @Param("example") RoleResourceExample example);


    int deleteRoleResourceByResourceId(Integer resourceid);

    int deleteRoleResourceByResourceIds(List<Integer> resourceIds);

    int deleteRoleResources(List<Integer> resourceIds);

    int deleteRoleResource(@Param("roleId") Integer roleId);

}