package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.Partner;
import com.chinaxiaopu.xiaopuMobi.model.PartnerExample;
import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.partner.GroupPartnerVo;
import com.chinaxiaopu.xiaopuMobi.vo.partner.SchoolPartherPassVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PartnerMapper {
    long countByExample(PartnerExample example);

    int deleteByExample(PartnerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Partner record);

    int insertSelective(Partner record);

    List<Partner> selectByExample(PartnerExample example);

    Partner selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Partner record, @Param("example") PartnerExample example);

    int updateByExample(@Param("record") Partner record, @Param("example") PartnerExample example);

    int updateByPrimaryKeySelective(Partner record);

    int updateByPrimaryKey(Partner record);

    @Select("SELECT count(1) FROM partners WHERE MOBILE = #{mobile,jdbcType=BIGINT} and type=1")
    int selectCountByMobile(@Param("mobile") Long mobile); //判断是否加入校园合伙人

    @Select("SELECT count(1) FROM partners WHERE MOBILE = #{mobile,jdbcType=BIGINT} and type=2")
    int selectCountByMobile1(@Param("mobile") Long mobile); //判断是否加入社团合伙人

    List<Partner> selectPartnerList(Partner partner);

    int updateByStatus(Partner partner);

    Partner selectPartnerDetails(Partner partner);

    @Select("SELECT count(1) FROM partners WHERE status = 1 and type = 1")
    int selectCountBySchool();

    @Select("SELECT count(1) FROM partners WHERE status = 1 and type = 2")
    int selectCountByGroup();

    List<Partner> selectPartnerListByStatusFail(Partner partner);//查询校园所有待审核和未通过

    List<SchoolPartherPassVo> selectPartnerListByStatusPass(Partner partner);//查询校园所有通过状态

    List<GroupPartnerVo> selectGroupPartnerListByStatusFail(Partner partner); //查询社团所有待审核和未通过

    List<GroupPartnerVo> selectGroupPartnerListByStatusPass(Partner partner); //查询社团所有通过状态

    GroupPartnerVo selectPartnerByGroupId(@Param("groupId")Integer groupId); //根据社团id查询详情


}