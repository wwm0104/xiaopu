package com.chinaxiaopu.xiaopuMobi.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by Wang on 2016/12/01.
 */

public class TicketVo {
    @Setter @Getter
    private Integer id;
    @Setter @Getter
    private Integer ticketId;
    @Setter @Getter
    private Integer eventId;
    @Setter @Getter
    private Integer businessType;
    @Setter @Getter
    private String eventSubject;
    @Setter @Getter
    private String posterImg;
    @Setter @Getter
    private Integer ticketCnt = 1;
    @Setter @Getter @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date startTime;
    @Setter @Getter @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date endTime;
    @Setter @Getter
    private String eventAddress;
    @Setter @Getter
    private String ticketQrcode;
    @Setter @Getter
    private Integer ticketStatus;

}
