package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicGroupDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicUserInfoDto;
import com.chinaxiaopu.xiaopuMobi.model.GroupMember;
import com.chinaxiaopu.xiaopuMobi.model.GroupMemberExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GroupMemberMapper {
    long countByExample(GroupMemberExample example);

    int deleteByExample(GroupMemberExample example);

    int insert(GroupMember record);

    int insertSelective(GroupMember record);

    List<GroupMember> selectByExample(GroupMemberExample example);

    int updateByExampleSelective(@Param("record") GroupMember record, @Param("example") GroupMemberExample example);

    int updateByExample(@Param("record") GroupMember record, @Param("example") GroupMemberExample example);

    GroupMember selectByGroupMember(GroupMember groupMember);

    GroupMember selectByGroupMemberAndStatus(GroupMember groupMember);

    int updateByGroupMember(GroupMember groupMember);

    int selectByUserOpt(GroupMember groupMember);

    int getApplyCount(@Param("userId")Integer userId, @Param("groupId")Integer groupId, @Param("status")Integer status);

    List<GroupMember> selectByGroupMemberAndKeyword(@Param("groupMember")GroupMember groupMember, @Param("keyword")String keyword, @Param("userId")Integer userId);

    GroupMember selectByGroupIdAndMemberId(@Param("groupId")Integer groupId, @Param("memberId")Integer memberId);

    int confirmGroupMember(GroupMember groupMember);

    GroupMember selectStatusByGroupMember(@Param("groupId")Integer groupId, @Param("memberId")Integer memberId, @Param("status")Integer status);

    GroupMember selectStatusByGroupMemberAndStatus(@Param("groupId")Integer groupId, @Param("memberId")Integer memberId);

    int updateStatusByGroupMember(GroupMember groupMember);

    List<TopicUserInfoDto> selectYSHGroupMemberByGroupId(TopicGroupDto topicGroupDto);

    List<TopicUserInfoDto> selectWSHGroupMemberByGroupId(TopicGroupDto topicGroupDto);

    int checkIsPresident(Map<String, Object> map);
}