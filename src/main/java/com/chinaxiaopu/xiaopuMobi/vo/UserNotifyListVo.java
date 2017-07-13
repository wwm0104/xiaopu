package com.chinaxiaopu.xiaopuMobi.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * Created by Wang
 * date: 2016/12/09
 */
@Data
public class UserNotifyListVo {

    private Date timePoint;
    private List<UserNotifyVo> list;
}
