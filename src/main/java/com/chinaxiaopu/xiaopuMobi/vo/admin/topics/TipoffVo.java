package com.chinaxiaopu.xiaopuMobi.vo.admin.topics;

import com.chinaxiaopu.xiaopuMobi.model.Tipoff;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * Created by hao on 2016/11/10.
 */
@Data
public class TipoffVo extends Tipoff{
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date times;
    private String realName;
}
