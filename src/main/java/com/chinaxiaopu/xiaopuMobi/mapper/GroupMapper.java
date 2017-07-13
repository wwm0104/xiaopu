package com.chinaxiaopu.xiaopuMobi.mapper;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.EventMemberVo;
import com.chinaxiaopu.xiaopuMobi.vo.GroupMemberVo;
import com.chinaxiaopu.xiaopuMobi.vo.partner.GroupListVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.chinaxiaopu.xiaopuMobi.model.Group;
import com.chinaxiaopu.xiaopuMobi.model.GroupExample;

public interface GroupMapper {
    long countByExample(GroupExample example);

    int deleteByExample(GroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Group record);

    int insertSelective(Group record);

    List<Group> selectByExample(GroupExample example);

    Group selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Group record, @Param("example") GroupExample example);

    int updateByExample(@Param("record") Group record, @Param("example") GroupExample example);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);

    List<Group> getMyGroupListByUserId(@Param("userId")Integer userId,@Param("status")Integer status);

    @Select("SELECT * FROM groups WHERE id IN (SELECT group_id FROM group_members WHERE member_id=#{userId})")
    List<Group> getConcernGroupListByUserId(Integer userId);

    List<Group> getGroupListByGroup(Group group);

    List<Group> getGroupsByEventId(Integer eventId);

    List<Group> confirmUserJoinEvent(@Param("eventId")Integer eventId, @Param("userId")Integer userId);

    int selectBySchoolNameAndName(@Param("schoolId")Integer schoolId, @Param("name")String name);

    List<GroupListVo> selectByMobile(@Param("mobile")Long mobile); //根据手机查询社团列表

    List<String> selectGroupNameyPrizeLogId(@Param("id") Integer id);//根据发奖记录id查询发奖人所有社团(新)

    List<String> selectByUserId(@Param("id") Integer id); //根据用户id查询所属社团

    @Select(" SELECT COUNT(1) FROM groups where president_id = #{userId}")
    int selectCntByUserId(@Param("userId")Integer userId); //是否为社长

    List<GroupMemberVo> getGroupListByUserId(Integer userId);

}