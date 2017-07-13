package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.UserRole;
import com.chinaxiaopu.xiaopuMobi.vo.authorization.UserRoleVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */
public interface UserRoleMapper {
    int insert(UserRole userRole);
    int deleteByPrimaryKey(Integer id);
    @Select("SELECT count(1) FROM user_role WHERE user_id = #{userId,jdbcType=INTEGER} and role_id=#{roleId,jdbcType=INTEGER}")
    int selectByUserId(@Param("userId") Integer userId,@Param("roleId") Integer roleId);  //判断当前人的权限


    @Delete("DELETE from user_role where user_id=#{userId} and role_id=#{roleId}")
    int deleteById(@Param("userId") Integer userId,@Param("roleId") Integer roleId);  //删除权限

    //
//    @Select("SELECT count(1) FROM user_role WHERE user_id = #{userId,jdbcType=INTEGER} and role_id=1")
//    int selectByUserId(@Param("userId") Integer userId);

    int deleteByRoleId(Integer roleId);

    int insertUserRoleList(List<UserRole> userRoleList);

    @Select("select role_id from user_role where user_id = #{userId,jdbcType=INTEGER}")
    int selectRoleIdByUserId(@Param("userId") Integer userId);

    @Select("select * from user_role where user_id = #{userId,jdbcType=INTEGER}")
    List<UserRole> selectUserRoleListByUserId(@Param("userId") Integer userId);

}
