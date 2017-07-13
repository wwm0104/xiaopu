package com.chinaxiaopu.xiaopuMobi.code;

import lombok.Data;


/**
 * 系统登录接口返回json内容类
 * Created by liuwei
 * date: 16/5/27
 */
public @Data class LoginResult extends AbstractResult{

    private static final long serialVersionUID = 5576237395711742681L;

    private Integer resultCode;// -- 0：登录失败，1：登录成功；2：系统错误；11：手机号码为空；12：用户不存在；21：密码为空；22：密码错误；31：验证码为空；32：验证码错误；
    private String clientDigest;// --客户端唯一表示，如果和你发起请求的不匹配，则表示此次请求的返回结果作废。
    private Integer userId;// --客户端唯一表示，如果和你发起请求的不匹配，则表示此次请求的返回结果作废。
    private String accessToken;// -- 用户登录后信任凭证（后续如果有需要验证登录的接口都要带上此参数）

    private String msg;

}
