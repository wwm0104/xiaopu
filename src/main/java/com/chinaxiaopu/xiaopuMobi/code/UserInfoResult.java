package com.chinaxiaopu.xiaopuMobi.code;


import lombok.Data;


/**
 * 系统接口返回json内容类
 * Created by liuwei
 * date: 16/5/27
 */
public @Data class UserInfoResult<E> extends AbstractResult{

    private static final long serialVersionUID = 5576237395711742681L;

    public static final int SUCCESS = 1; //正常

    public static final int FAILURE = 0;//错误

    private int resultCode;

    private E obj = null;
    
    private Integer groupCount;
    
    private Integer eventCount;

    private String msg;

}
