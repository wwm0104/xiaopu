package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.EventMember;
import com.chinaxiaopu.xiaopuMobi.model.EventMemberExample;
import com.chinaxiaopu.xiaopuMobi.vo.UserInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventMemberMapper {
    long countByExample(EventMemberExample example);

    int deleteByExample(EventMemberExample example);

    int insert(EventMember record);

    int insertSelective(EventMember record);

    List<EventMember> selectByExample(EventMemberExample example);

    int updateByExampleSelective(@Param("record") EventMember record, @Param("example") EventMemberExample example);

    int updateByExample(@Param("record") EventMember record, @Param("example") EventMemberExample example);

    int selectByUserOpt(EventMember eventMember);

    EventMember selectByIsFollow(EventMember eventMember);

    int updateByEventMember(EventMember eventMember);

    int getApplyCount(@Param("userId")Integer userId,@Param("eventId")Integer eventId,@Param("status")Integer status);

    List<EventMember> selectByEventMemberAndKeyword(@Param("eventMember")EventMember eventMember, @Param("keyword") String keyword, @Param("userId") Integer userId);

    EventMember selectByEventMember(@Param("eventId")Integer eventId, @Param("memberId")Integer memberId, @Param("isJoin")Integer isJoin, @Param("status")Integer status);

    int confirmEventMember(EventMember eventMember);

    int updateStatusByEventMember(EventMember eventMember);

    int getUntreatedApplyCountByEventId(Integer eventId);

    List<UserInfoVo> selectUserInfoByEventIdAndStatus(@Param("eventId")Integer eventId, @Param("status")Integer status);
}