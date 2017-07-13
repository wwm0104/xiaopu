package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.UserInvitationCode;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Administrator on 2016/10/28.
 */
public interface UserInvitationCodeMapper {
    int insert(UserInvitationCode userInvitationCode);


    @Select("SELECT count(1) FROM user_invitation_code WHERE user_code = #{code,jdbcType=VARCHAR}")
    int selectByCode(@Param("code") String code); //查询邀请码是否有效

    UserInvitationCode selectByUserId(@Param("userId") Integer id);  //根据用户id查询邀请码

    @Select("select count(1) from user_invitation_code where invitation_code=#{userCode,jdbcType=VARCHAR}")
    int selectInvitationCodeCntByUserCode(@Param("userCode") final String userCode);

}
