package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.UserWeixin;
import com.chinaxiaopu.xiaopuMobi.model.UserWeixinExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserWeixinMapper {
    long countByExample(UserWeixinExample example);

    int deleteByExample(UserWeixinExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UserWeixin record);

    int insertSelective(UserWeixin record);

    List<UserWeixin> selectByExample(UserWeixinExample example);

    UserWeixin selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UserWeixin record, @Param("example") UserWeixinExample example);

    int updateByExample(@Param("record") UserWeixin record, @Param("example") UserWeixinExample example);

    int updateByPrimaryKeySelective(UserWeixin record);

    int updateByPrimaryKey(UserWeixin record);

    UserWeixin selectByOpenId(@Param("openid") String openid);

}