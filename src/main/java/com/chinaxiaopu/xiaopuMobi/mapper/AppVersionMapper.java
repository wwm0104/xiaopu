package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.AppVersion;
import com.chinaxiaopu.xiaopuMobi.model.AppVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppVersionMapper {
    long countByExample(AppVersionExample example);

    int deleteByExample(AppVersionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppVersion record);

    int insertSelective(AppVersion record);

    List<AppVersion> selectByExample(AppVersionExample example);

    AppVersion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppVersion record, @Param("example") AppVersionExample example);

    int updateByExample(@Param("record") AppVersion record, @Param("example") AppVersionExample example);

    int updateByPrimaryKeySelective(AppVersion record);

    int updateByPrimaryKey(AppVersion record);

    AppVersion selectLastVersionByAppId(final String appId);

    List<AppVersion> selectByAppNameOrAppIdGetList(AppVersion appVersion);
}