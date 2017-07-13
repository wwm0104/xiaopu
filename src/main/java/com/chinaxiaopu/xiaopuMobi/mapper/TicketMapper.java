package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.Ticket;
import com.chinaxiaopu.xiaopuMobi.model.TicketExample;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.TicketVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TicketMapper {
    long countByExample(TicketExample example);

    int deleteByExample(TicketExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ticket record);

    int insertSelective(Ticket record);

    List<Ticket> selectByExample(TicketExample example);

    Ticket selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ticket record, @Param("example") TicketExample example);

    int updateByExample(@Param("record") Ticket record, @Param("example") TicketExample example);

    int updateByPrimaryKeySelective(Ticket record);

    int updateByPrimaryKey(Ticket record);

    Integer getRemainingTicket(@Param("businessId") Integer businessId, @Param("businessType") Integer businessType);

    Ticket selectByBusinessIdAndType(@Param("businessId") Integer businessId, @Param("businessType") Integer businessType);

    List<TicketVo> ticketList(Integer userId);

    TicketVo ticketInfo(@Param("ticketId") Integer ticketId, @Param("userId") Integer userId);

    @Select("select count(1) from tickets where create_id=#{userId,jdbcType=INTEGER}")
    int selectCntByUserId(@Param("userId") Integer userId);  //判断是否拥有电子门票活动

    Integer isNeedTicket(@Param("businessId") Integer businessId, @Param("businessType") Integer businessType);

    Integer getSignCnt(@Param("businessId") Integer businessId, @Param("businessType") Integer businessType);

    /**
     * 判断是否存在 电子门票
     *
     * @param example
     * @return
     */
    long isTicket(TicketExample example);
}