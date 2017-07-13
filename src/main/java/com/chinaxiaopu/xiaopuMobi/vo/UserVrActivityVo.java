package com.chinaxiaopu.xiaopuMobi.vo;

import com.chinaxiaopu.xiaopuMobi.model.VrActivity;
import lombok.Data;

import java.util.List;

/**
 * Created by ellien
 * date: 2016/11/12
 */
public @Data class UserVrActivityVo {

    private String schoolName;

    private Long mobile;

    private String realName;

    private String studentNo;

    private Integer appointmentCnt;

    private List<VrActivity> list;
}
