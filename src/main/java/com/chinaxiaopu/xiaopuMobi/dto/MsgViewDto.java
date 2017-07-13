package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuwei
 * date: 2016/12/6
 */
@Data
public class MsgViewDto implements Serializable {
    private int id;
    private int type;
    private String name;
    private Long expireTime;
}
