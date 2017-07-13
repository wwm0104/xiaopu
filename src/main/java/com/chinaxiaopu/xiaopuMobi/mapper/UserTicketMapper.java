package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.UserTicket;
import com.chinaxiaopu.xiaopuMobi.model.UserTicketExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserTicketMapper {
    long countByExample(UserTicketExample example);

    int deleteByExample(UserTicketExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserTicket record);

    int insertSelective(UserTicket record);

    List<UserTicket> selectByExample(UserTicketExample example);

    UserTicket selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserTicket record, @Param("example") UserTicketExample example);

    int updateByExample(@Param("record") UserTicket record, @Param("example") UserTicketExample example);

    int updateByPrimaryKeySelective(UserTicket record);

    int updateByPrimaryKey(UserTicket record);

    UserTicket selectTicketInfo(UserTicket userTicket);

    int updateStatusByid(UserTicket userTicket);

    @Select("select count(1) from user_ticket where user_id=#{userId,jdbcType=INTEGER}")
    int selectByUserId(@Param("userId") Integer userId);  //判断当前人的权限
}