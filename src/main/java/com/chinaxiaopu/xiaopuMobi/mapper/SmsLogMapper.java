package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.SmsLog;
import com.chinaxiaopu.xiaopuMobi.model.SmsLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsLogMapper {
    long countByExample(SmsLogExample example);

    int deleteByExample(SmsLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsLog record);

    int insertSelective(SmsLog record);

    List<SmsLog> selectByExample(SmsLogExample example);

    SmsLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsLog record, @Param("example") SmsLogExample example);

    int updateByExample(@Param("record") SmsLog record, @Param("example") SmsLogExample example);

    int updateByPrimaryKeySelective(SmsLog record);

    int updateByPrimaryKey(SmsLog record);
}