package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.InvitationCode;
import com.chinaxiaopu.xiaopuMobi.model.InvitationCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface InvitationCodeMapper {
    long countByExample(InvitationCodeExample example);

    int deleteByExample(InvitationCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InvitationCode record);

    int insertSelective(InvitationCode record);

    List<InvitationCode> selectByExample(InvitationCodeExample example);

    InvitationCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InvitationCode record, @Param("example") InvitationCodeExample example);

    int updateByExample(@Param("record") InvitationCode record, @Param("example") InvitationCodeExample example);

    int updateByPrimaryKeySelective(InvitationCode record);

    int updateByPrimaryKey(InvitationCode record);

    @Select("SELECT count(1) FROM invitation_code WHERE code = #{code,jdbcType=VARCHAR}")
    int selectByCode(@Param("code") String code);
}