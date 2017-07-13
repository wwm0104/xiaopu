package com.chinaxiaopu.xiaopuMobi.dto.topic;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;

/**
 * Created by ycy on 2016/12/1.
 */
public class TicketTakeDto extends ContextDto {
    private Integer ticketUserId;
    private Integer ticketId;
    private Integer businessId;
    private Integer businessType;
    private Integer userId;

    public Integer getTicketUserId() {
        return ticketUserId;
    }

    public void setTicketUserId(Integer ticketUserId) {
        this.ticketUserId = ticketUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }
}
