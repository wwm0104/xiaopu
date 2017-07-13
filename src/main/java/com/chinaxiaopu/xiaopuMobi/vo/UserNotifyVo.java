package com.chinaxiaopu.xiaopuMobi.vo;

import com.chinaxiaopu.xiaopuMobi.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import lombok.Data;

import java.util.Date;
import java.util.Map;


/**
 * Created by liuwei
 * date: 2016/12/7
 */
@Data
public class UserNotifyVo {

    private int id;
    private Integer isRead;
    private int type;
    private String content;
    private String parameter;
    private Map<String,Object> parameterMap;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String showTime;


    public Map<String, Object> getParameterMap() {
        parameterMap = new Gson().fromJson(parameter,Map.class);
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public String getShowTime() {
        showTime = DateTimeUtil.getShowTime2(getCreateTime());
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }
}
