package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.GroupCategory;
import com.chinaxiaopu.xiaopuMobi.model.GroupCategoryExample;
import java.util.List;

import com.chinaxiaopu.xiaopuMobi.model.School;
import org.apache.ibatis.annotations.Param;

public interface GroupCategoryMapper {
    long countByExample(GroupCategoryExample example);

    int deleteByExample(GroupCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GroupCategory record);

    int insertSelective(GroupCategory record);

    List<GroupCategory> selectByExample(GroupCategoryExample example);

    GroupCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GroupCategory record, @Param("example") GroupCategoryExample example);

    int updateByExample(@Param("record") GroupCategory record, @Param("example") GroupCategoryExample example);

    int updateByPrimaryKeySelective(GroupCategory record);

    int updateByPrimaryKey(GroupCategory record);

    /**
     * 获取所有的社团类别
     *
     * @return
     */
    List<School> getgroupList();
}