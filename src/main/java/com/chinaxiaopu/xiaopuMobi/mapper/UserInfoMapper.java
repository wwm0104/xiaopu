package com.chinaxiaopu.xiaopuMobi.mapper;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.dto.authorization.UserInfoSelectDto;
import com.chinaxiaopu.xiaopuMobi.dto.VrDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.PersonnelDto;
import com.chinaxiaopu.xiaopuMobi.dto.partner.PartnerDetailsDto;
import com.chinaxiaopu.xiaopuMobi.vo.UserInfoVo;
import com.chinaxiaopu.xiaopuMobi.vo.VrUserVo;
import com.chinaxiaopu.xiaopuMobi.vo.audio.PersonnelVo;
import com.chinaxiaopu.xiaopuMobi.vo.authorization.UserRoleVo;
import com.chinaxiaopu.xiaopuMobi.vo.partner.SchoolPartnerVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.model.UserInfoExample;

public interface UserInfoMapper {
    long countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example);

    UserInfo selectByPrimaryKey(Integer userId);

    List<UserRoleVo> selectUserInfoByKeyWord(UserInfoSelectDto userInfoSelectDto);


    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    List<UserInfo> selectUserInfoByRoleId(UserInfo userInfo);

    @Select("SELECT * FROM user_info WHERE user_id IN (SELECT friend_id FROM friend_table WHERE user_id = #{userId})")
    List<UserInfo> getFriendListByUserId(Integer userId);

    UserInfo getUserInfoByMobile(@Param("mobile") Long mobile);

    UserInfo selectUserInfoById(Integer userId);

    //根据邀请码查询子集邀请人信息
    List<SchoolPartnerVo> selectUserInfoByCode(PartnerDetailsDto partnerDetailsDto);

    //根据合伙人id查询所有下级信息
    List<SchoolPartnerVo> selectUserInfoByPartner(PartnerDetailsDto partnerDetailsDto);

    //查询Vr用户预约
    List<VrUserVo> selectVrList(VrDto vrDto);

    //查询昵称是否存在
    @Select("SELECT count(1) FROM user_info WHERE nick_name = #{nickName,jdbcType=VARCHAR}")
    int selectByNickName(@Param("nickName") String nickName);

    //查询人员列表
    List<PersonnelVo> selectAnchorInfo(PersonnelDto personnelDto);

    List<UserInfo> selectAll();

    List<UserRoleVo> selectUserRoleVoList(UserInfoSelectDto userInfoSelectDto);

    UserRoleVo selectUserRoleById(int userId);

    List<UserInfo> selectUserInfoByRoleId(int roleId);

    List<UserInfo> selectUserInfoListByRoleId(int roleId);

    List<UserInfo> selectNotInRoleByKeyWord(UserInfoSelectDto userInfoSelectDto);

}