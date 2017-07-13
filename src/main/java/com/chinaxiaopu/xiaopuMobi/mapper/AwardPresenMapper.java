package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.AwardPresen;
import com.chinaxiaopu.xiaopuMobi.model.AwardPresenExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AwardPresenMapper {
    long countByExample(AwardPresenExample example);

    int deleteByExample(AwardPresenExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AwardPresen record);

    int insertSelective(AwardPresen record);

    List<AwardPresen> selectByExample(AwardPresenExample example);

    AwardPresen selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AwardPresen record, @Param("example") AwardPresenExample example);

    int updateByExample(@Param("record") AwardPresen record, @Param("example") AwardPresenExample example);

    int updateByPrimaryKeySelective(AwardPresen record);

    int updateByPrimaryKey(AwardPresen record);

    List<AwardPresen> selectAllPerson(AwardPresen awardPresen);

    int checkUserInfo(Map<String,Object> map);

    int checkPhone(Map<String,Object> map);

    int updatePresen(AwardPresen awardPresen);

    int checkIsAwardPerson(Integer userId);
    //根据手机查询是否为发奖人
    @Select("SELECT count(1) FROM award_presens WHERE mobile = #{mobile} and available=1")
    int selectByMobile(@Param("mobile") long mobile);

    int updateAwardCnt(Integer userId);
}