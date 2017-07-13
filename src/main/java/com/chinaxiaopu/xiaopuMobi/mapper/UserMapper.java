package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.User;
import com.chinaxiaopu.xiaopuMobi.model.UserExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    @Select("SELECT * FROM users WHERE MOBILE = #{mobile,jdbcType=BIGINT}")
    User selectByMobile(@Param("mobile") Long mobile);
    
    @Select("SELECT id FROM users WHERE MOBILE = #{mobile,jdbcType=BIGINT}")
	int selectIdByMobile(@Param("mobile") Long mobile);

    @Select("SELECT count(1) FROM users WHERE mobile = #{mobile,jdbcType=BIGINT}")
    int selectCountByMobile(@Param("mobile") Long mobile);

}