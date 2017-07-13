package com.chinaxiaopu.xiaopuMobi.code;


import lombok.Data;


/**
 * 系统接口返回json内容类
 * Created by liuwei
 * date: 16/5/27
 */
public @Data class Result<E> extends AbstractResult {

    private static final long serialVersionUID = 5576237395711742681L;

    public static final int isAudit = 5; //审核中

    public static final int isPresident = 4; //是社长

    public static final int UNAPPLY = 3; //已加入

    public static final int APPLY = 2; //能申请

    public static final int SUCCESS = 1; //正常

    public static final int FAILURE = 0;//错误

    /**
     * 异常msg定义
     */
    public static final String NOT_LOGGED_IN = "用户未登录，请先登录";
    public static final String SERVER_EXCEPTION = "服务器异常,请稍后重试";
    public static final String NO_DATA = "没有数据";
    public static final String NO_JURISDICTION = "该用户没有权限";

    private int resultCode;

    private String msg;

    private E obj = null;

    private E path=null;


}
