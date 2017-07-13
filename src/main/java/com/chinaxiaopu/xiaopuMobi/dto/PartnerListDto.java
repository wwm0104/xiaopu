package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

import java.util.Date;

/**
 * 应用在:
 *   合伙人列表
 * Created by flint
 * Date: 2016/10/24.
 */
@Data
public class PartnerListDto extends ContextDto{
    private static final long serialVersionUID = 1L;

    private Long mobile;

    private String realName;

    private Integer status;

    private Date joinTime;

    private String checkInfo;

    private Date checkTime;
}
