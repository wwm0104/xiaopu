package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.Resource;
import com.chinaxiaopu.xiaopuMobi.model.ResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResourceMapper {
    long countByExample(ResourceExample example);

    int deleteByExample(ResourceExample example);

    int deleteByPrimaryKey(Integer id);

    int deleteByIds(List<Integer> ids);

    int insert(Resource record);

    int insertSelective(Resource record);

//    int insertBySelective(Resource resource);

    List<Resource> selectResourceByRoleId(Integer roleId);

    Resource selectByName(String name);

    List<Resource> selectByParentId(Integer parentId);

    List<Resource> selectByExample(ResourceExample example);

    Resource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByExample(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

    List<Resource> selectAll();

    List<Resource> findAll();

    List<Resource> selectByPermission(String permission);
}