package com.chinaxiaopu.xiaopuMobi.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Wang on 2016/12/01.
 */
public class TicketListVo extends  ContextVo{
    @Setter @Getter
    private List<TicketVo> ticketList;

}