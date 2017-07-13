package com.chinaxiaopu.xiaopuMobi.dto;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 应用在：
 *
 * @author Wang
 */
public
@Data
class EventPublishDto extends ContextDto {

    private Integer eventId;//活动ID，用于活动修改
    private Integer creatorId;//社长ID
    private Integer organizeId;//社团ID
    private String organizeName;//社团名称
    private Integer schoolId;//学校ID
    private String schoolName;//学校名称
    private String subject;//活动主题

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date startTime;//开始时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date endTime;//结束时间
    private String address;//活动地址
    private Integer joinType;//活动参与类型
    private String description;//活动介绍
    private String qrcode;//二维码base64编码

    private String smallPosterImg;
    private String posterImg;//活动海报
    private String[] contentImgs;//活动图片
    private String further;//活动图片的宽高

    private Integer ticket;//是否需要门票，1需要，0不需要
    private Integer ticketCnt;//需要时，填写的门票数量
}
